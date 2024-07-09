package com.dss.ia.services;

import com.dss.ia.model.Answer;
import com.dss.ia.model.Question;

public interface OpenAIService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
}
