package com.dss.ia.services;

import com.dss.ia.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    private final ImageClient imageClient;

    public OpenAIServiceImpl(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    @Override
    public byte[] getImage(Question question) {

        var options = ImageOptionsBuilder.builder()
                .withHeight(1024).withWidth(1024)
                .withResponseFormat("b64_json")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(question.question(), options);

        var imageResponse = imageClient.call(imagePrompt);
        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());

    }

}
