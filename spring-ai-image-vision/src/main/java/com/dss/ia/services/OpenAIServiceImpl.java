package com.dss.ia.services;

import com.dss.ia.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    private final ImageModel imageClient;
    private final ChatModel chatClient;

    public OpenAIServiceImpl(ImageModel imageClient, ChatModel chatClient) {
        this.imageClient = imageClient;
        this.chatClient = chatClient;
    }

    @Override
    public byte[] getImage(Question question) {

        var options = OpenAiImageOptions.builder()
                .withHeight(1024).withWidth(1024)
                .withResponseFormat("b64_json")
                .withModel("dall-e-3")
                .withQuality("hd")
                .withStyle("natural")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(question.question(), options);

        var imageResponse = imageClient.call(imagePrompt);
        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());

    }

    @Override
    public String getDescription(MultipartFile file) throws IOException {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_VISION_PREVIEW.getValue())
                .build();

        var userMessage = new UserMessage(
                "Explain what do you see in this picture?", // content
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG, file.getBytes()))); // media

        return chatClient.call(new Prompt(List.of(userMessage), chatOptions)).getResult().getOutput().toString();
    }

}
