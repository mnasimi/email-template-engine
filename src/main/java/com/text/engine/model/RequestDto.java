package com.text.engine.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RequestDto {
  @NotBlank
  String firstName;
  @NotBlank
  String lastName;
  @NotBlank
  Gender gender;
  @NotBlank
  String street;
  @NotBlank
  String houseNumber;
  @NotBlank
  String zipCode;
  @NotBlank
  String city;
  String recipientEmail;
  String customField;
}
