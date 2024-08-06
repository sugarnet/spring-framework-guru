package com.dss.ia.bootstrap;

import com.dss.ia.config.VectorStoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadVectorStore implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadVectorStore.class);
    private final VectorStore vectorStore;
    private final VectorStoreProperties vectorStoreProperties;

    public LoadVectorStore(VectorStore vectorStore, VectorStoreProperties vectorStoreProperties) {
        this.vectorStore = vectorStore;
        this.vectorStoreProperties = vectorStoreProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vectorStore.similaritySearch("Sportsman").isEmpty()) {
            LOGGER.debug("Loading documents into vector store");

            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                LOGGER.debug("Loading vector store document: {}", document);

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();

                TextSplitter textSplitter = new TokenTextSplitter();

                List<Document> splitDocuments = textSplitter.apply(documents);
                vectorStore.add(splitDocuments);
            });
        }
        LOGGER.debug("Loaded vector store");
    }
}
