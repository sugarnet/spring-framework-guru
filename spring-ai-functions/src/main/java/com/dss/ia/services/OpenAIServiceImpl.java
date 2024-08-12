package com.dss.ia.services;

import com.dss.ia.functions.WeatherServiceFunction;
import com.dss.ia.model.Answer;
import com.dss.ia.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    @Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjasKey;

    private final OpenAiChatClient openAiChatClient;

    public OpenAIServiceImpl(OpenAiChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    @Override
    public Answer getAnswer(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new WeatherServiceFunction(apiNinjasKey))
                        .withName("CurrentWeather")
                        .withDescription("Get the current weather for a location")
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        var response = openAiChatClient.call(new Prompt(List.of(userMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());
    }

}
