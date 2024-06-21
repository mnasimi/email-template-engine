package com.text.engine.service;

import com.text.engine.model.RequestDto;
import com.text.engine.model.templates.GoodByeTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodByeTextEngineService {

  private final EmailTemplatingService emailTemplatingService;

  public String processText(RequestDto request) {

    var template = GoodByeTemplate.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .gender(request.getGender())
        .build();

    return emailTemplatingService.generateContent(template);
  }
}
