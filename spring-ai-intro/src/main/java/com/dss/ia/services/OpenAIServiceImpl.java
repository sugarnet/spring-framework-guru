package com.dss.ia.services;


import com.dss.ia.model.Answer;
import com.dss.ia.model.GetCapitalRequest;
import com.dss.ia.model.GetCapitalResponse;
import com.dss.ia.model.GetCapitalWithInfoResponse;
import com.dss.ia.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    private final ChatClient chatClient;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfo;

    public OpenAIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getAnswer(String question) {
        return getSimpleAnswer(question);
    }

    @Override
    public Answer getAnswer(Question question) {
        return new Answer(getSimpleAnswer(question.question()));
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {
        LOGGER.info("getCapital {}", getCapitalRequest);
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        return getResponse(getCapitalRequest, parser, getCapitalPrompt);
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        LOGGER.info("getCapitalWithInfo {}", getCapitalRequest);
        BeanOutputParser<GetCapitalWithInfoResponse> parser = new BeanOutputParser<>(GetCapitalWithInfoResponse.class);
        return getResponse(getCapitalRequest, parser, getCapitalPrompt);
    }

    private <T extends Record> T getResponse(GetCapitalRequest getCapitalRequest, BeanOutputParser<T> parser, Resource resource) {
        String format = parser.getFormat();
        System.out.println("Format: \n" + format);
        PromptTemplate promptTemplate = new PromptTemplate(resource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse response = chatClient.call(prompt);

        String answer = response.getResult().getOutput().getContent();
        System.out.println("Answer" + answer);

        return parser.parse(answer);
    }

    private String getSimpleAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        try {
            ChatResponse chatResponse = chatClient.call(prompt);
            return chatResponse.getResult().getOutput().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
