package com.text.engine.annotation;

import java.math.BigDecimal;

import com.text.engine.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FieldVariableFormat {
  IDENTITY(Object.class),
  GENDER(Gender.class),
  GREETING(Gender.class),
  AMOUNT(BigDecimal.class),
  IBAN(String.class),
  CAPITALIZED(String.class),
  AND(String[].class),
  OR(String[].class),
  CUSTOM_FIELD(Object.class);

  private final Class<?> targetType;
}
