package com.text.engine.templates.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.ResourceLoader;

@RequiredArgsConstructor
public class EmailManagementTemplating {

  private final ResourceLoader resourceLoader;
  private final String resourcePath;

  public String createContent(Map<String, String> variables) {
    return substituteString(variables, resourcePath);
  }

  private String substituteString(Map<String, String> placeholder, String filePath) {
    String descriptionTemplate;
    try {
      var resource = resourceLoader.getResource(filePath);
      descriptionTemplate = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot locate file %s - error: %s".formatted(filePath, e.getMessage()));
    }

    var stringSubstitutor = new StringSubstitutor(placeholder)
        .setEnableUndefinedVariableException(false);

    return stringSubstitutor.replace(descriptionTemplate);
  }
}
