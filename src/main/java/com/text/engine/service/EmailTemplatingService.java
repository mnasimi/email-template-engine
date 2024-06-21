package com.text.engine.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.text.engine.annotation.FieldVariable;
import com.text.engine.annotation.FieldVariableFormat;
import com.text.engine.model.Gender;
import com.text.engine.templates.email.EmailManagementTemplating;
import com.text.engine.templates.email.Template;
import com.text.engine.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import static com.text.engine.model.templates.EmailTemplate.capitalizeName;
import static com.text.engine.utils.MiscUtils.extractGender;
import static com.text.engine.utils.MiscUtils.extractGreeting;
import static com.text.engine.utils.MiscUtils.formatAmount;
import static com.text.engine.utils.MiscUtils.formatIban;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class EmailTemplatingService {
  public static final DateTimeFormatter DMY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  private final ResourceLoader resourceLoader;

  String generateContent(Template template) {
    var discoveredFields = ReflectionUtils.discoverFields(template.getClass());
    var variableMap = discoveredFields
        .stream()
        .filter(field -> !field.isAnnotationPresent(FieldVariable.Exclude.class))
        .flatMap(field -> extractTemplateVariables(field, template))
        .collect(Collectors.toMap(TemplateVariable::variableName, TemplateVariable::value));
    return new EmailManagementTemplating(resourceLoader, template.getResourcePath()).createContent(variableMap);
  }

  private Stream<TemplateVariable> extractTemplateVariables(Field field, Template template) {
    var fieldValue = extractFieldValue(field, template);
    var variableAnnotations = field.getAnnotationsByType(FieldVariable.class);
    if (variableAnnotations.length == 0) {
      return Stream.of(new TemplateVariable(field.getName(), extractVariableValue(fieldValue)));
    }
    return Arrays
        .stream(variableAnnotations)
        .map(variableAnnotation -> new TemplateVariable(
            extractVariableName(field, variableAnnotation),
            extractVariableValue(fieldValue, variableAnnotation)
        ));
  }

  private Object extractFieldValue(Field field, Template template) {
    try {
      return ReflectionUtils.getFieldValue(field, template);
    } catch (Exception e) {
      throw new RuntimeException("Failed to extract the value of field %s, while generating email contents"
          .formatted(field.getName()), e);
    }
  }

  private String extractVariableName(Field field, FieldVariable annotation) {
    return StringUtils.isNotBlank(annotation.placeHolder()) ? annotation.placeHolder() : field.getName();
  }

  private String extractVariableValue(Object fieldValue) {
    return extractVariableValue(fieldValue, null);
  }

  private String extractVariableValue(Object fieldValue, FieldVariable annotation) {
    if (Objects.isNull(fieldValue) || StringUtils.isBlank(fieldValue.toString())) {
      return "";
    }

    var format = ofNullable(annotation).map(FieldVariable::formatter).orElse(FieldVariableFormat.IDENTITY);
    if (!format.getTargetType().isInstance(fieldValue)) {
      throw new RuntimeException("Incorrect type of the email variable field with the %s formatter".formatted(
          format));
    }

    if (fieldValue instanceof LocalDate dateValue) {
      return dateValue.format(DMY_DATE_TIME_FORMATTER);
    }

    return switch (format) {
      case IDENTITY -> fieldValue.toString();
      case IBAN -> formatIban(fieldValue.toString());
      case AMOUNT -> formatAmount((BigDecimal) fieldValue);
      case CAPITALIZED -> capitalizeName(fieldValue.toString());
      case AND -> StringUtils.join((String[]) fieldValue, " und ");
      case OR -> StringUtils.join((String[]) fieldValue, " oder ");
      case GENDER -> extractGender((Gender) fieldValue);
      case GREETING -> extractGreeting((Gender) fieldValue);
      case CUSTOM_FIELD -> StringUtils.join("(Aktenzeichen ", fieldValue.toString(), ")");
    };
  }

  private record TemplateVariable(String variableName, String value) {
  }
}
