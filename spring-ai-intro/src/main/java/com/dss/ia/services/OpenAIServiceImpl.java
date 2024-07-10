package com.dss.ia.services;


import com.dss.ia.model.Answer;
import com.dss.ia.model.GetCapitalRequest;
import com.dss.ia.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OpenAIServiceImpl.class);

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
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        LOGGER.info("getCapital {}", getCapitalRequest);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        LOGGER.info("getCapitalWithInfo {}", getCapitalRequest);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
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
