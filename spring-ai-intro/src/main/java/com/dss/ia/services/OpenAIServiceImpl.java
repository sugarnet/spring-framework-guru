package com.dss.ia.services;


import com.dss.ia.model.Answer;
import com.dss.ia.model.Question;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

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
