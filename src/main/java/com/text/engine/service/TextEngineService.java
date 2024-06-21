package com.text.engine.service;

import com.text.engine.model.templates.EmailTemplate;
import com.text.engine.model.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextEngineService {

  private final EmailTemplatingService emailTemplatingService;

  public String processText(RequestDto request) {

    var template = EmailTemplate.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .gender(request.getGender())
        .agentName("TODO")
        .customField(request.getCustomField())
        .build();

    return emailTemplatingService.generateContent(template);
  }
}
