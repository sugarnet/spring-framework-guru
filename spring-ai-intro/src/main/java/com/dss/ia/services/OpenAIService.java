package com.dss.ia.services;

import com.dss.ia.model.Answer;
import com.dss.ia.model.GetCapitalRequest;
import com.dss.ia.model.GetCapitalResponse;
import com.dss.ia.model.GetCapitalWithInfoResponse;
import com.dss.ia.model.Question;

public interface OpenAIService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);
    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

}
