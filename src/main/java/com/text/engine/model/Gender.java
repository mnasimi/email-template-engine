package com.text.engine.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {

  FEMALE("female", "Weiblich", "Frau"),
  MALE("male", "Männlich", "Herr");
  private final String value;
  private final String displayName;
  private final String salutation;

  public String getGermanSalutation() {
    return switch (this) {
      case MALE -> "Herr";
      case FEMALE -> "Frau";
      default -> "";
    };
  }
}
