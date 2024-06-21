package com.text.engine.model.templates;

import java.time.LocalDate;

import com.text.engine.annotation.FieldVariable;
import com.text.engine.annotation.FieldVariableFormat;
import com.text.engine.model.Gender;
import com.text.engine.templates.email.Template;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GoodByeTemplate implements Template {
  private static final String SUBJECT = "Abtretung unserer Kreditforderungen";
  private static final String RESOURCE_PATH = "classpath:templates/goodbye.html";

  @FieldVariable (formatter = FieldVariableFormat.GENDER)
  @FieldVariable(placeHolder = "greeting", formatter = FieldVariableFormat.GREETING)
  Gender gender;
  @FieldVariable(formatter = FieldVariableFormat.CAPITALIZED)
  String firstName;
  @FieldVariable(formatter = FieldVariableFormat.CAPITALIZED)
  String lastName;
  LocalDate dateOfSale;


  @Override
  public String getSubject() {
    return SUBJECT;
  }

  @Override
  public String getResourcePath() {
    return RESOURCE_PATH;
  }
}
