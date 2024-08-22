package com.dss.ia.services;

import com.dss.ia.model.Question;

public interface OpenAIService {
    byte[] getImage(Question question);

}
