package com.dss.ia.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
public class VectorStoreConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(VectorStoreConfig.class);
    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingClient embeddingClient, VectorStoreProperties vectorStoreProperties) {

        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingClient);
        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        if (!vectorStoreFile.exists()) {
            LOGGER.debug("Loading document into vector store");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                LOGGER.debug("Loading document {}", document.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> docs = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                simpleVectorStore.add(splitDocs);
            });
        } else {
            simpleVectorStore.load(vectorStoreFile);
        }
        simpleVectorStore.save(vectorStoreFile);
        return simpleVectorStore;

    }
}
