package com.dss.ia.controllers;

import com.dss.ia.model.Answer;
import com.dss.ia.model.GetCapitalRequest;
import com.dss.ia.model.GetCapitalResponse;
import com.dss.ia.model.GetCapitalWithInfoResponse;
import com.dss.ia.model.Question;
import com.dss.ia.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    private final OpenAIService openAIService;
    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }
    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/capitalWithInfo")
    public GetCapitalWithInfoResponse getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer createQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
}
