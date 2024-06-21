package com.text.engine.controller;

import com.text.engine.model.RequestDto;
import com.text.engine.service.GoodByeTextEngineService;
import com.text.engine.service.TextEngineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TextEngineController {
  private final TextEngineService textEngineService;
  private final GoodByeTextEngineService goodByeTextEngineService;

  @PostMapping("data")
  public ResponseEntity<String> getTextEngineService(@RequestBody RequestDto request) {
    return ResponseEntity.ok(textEngineService.processText(request));
  }

  @PostMapping("goodbye")
  public ResponseEntity<String> getGoodByeTextEngineService(@RequestBody RequestDto request) {
    return ResponseEntity.ok(goodByeTextEngineService.processText(request));
  }
}
