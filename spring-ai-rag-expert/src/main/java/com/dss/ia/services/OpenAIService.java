package com.dss.ia.services;

import com.dss.ia.model.Answer;
import com.dss.ia.model.Question;

public interface OpenAIService {
    Answer getAnswer(Question question);

}
