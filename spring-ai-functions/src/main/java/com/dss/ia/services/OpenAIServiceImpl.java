package com.dss.ia.services;

import com.dss.ia.model.Answer;
import com.dss.ia.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    @Override
    public Answer getAnswer(Question question) {
        LOGGER.debug("Get answer for {}", question);

        String response = "to-do...";
        LOGGER.debug("Response is {}", response);
        return new Answer(response);
    }

}
