package com.text.engine.model.templates;

import com.text.engine.annotation.FieldVariableFormat;
import com.text.engine.annotation.FieldVariable;
import com.text.engine.model.Gender;
import com.text.engine.templates.email.Template;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

@Value
@Builder
public class EmailTemplate implements Template {
  private static final String SUBJECT_TEMPLATE = "Forderungsaufstellung für C24 %s %s %s";
  private static final String RESOURCE_PATH = "classpath:templates/debt_setup.html";

  //@FieldVariable.Exclude
  @FieldVariable(formatter = FieldVariableFormat.GENDER)
  @FieldVariable(placeHolder = "greeting", formatter = FieldVariableFormat.GREETING)
  Gender gender;

  //@FieldVariable.Exclude
  @FieldVariable(formatter = FieldVariableFormat.CAPITALIZED)
  String firstName;
  @FieldVariable(formatter = FieldVariableFormat.CAPITALIZED)
  //@FieldVariable.Exclude
  String lastName;
  //@FieldVariable.Exclude

  @FieldVariable(formatter = FieldVariableFormat.CAPITALIZED)
  String agentName;
  @FieldVariable(formatter = FieldVariableFormat.CUSTOM_FIELD)
  String customField;

  @Override
  public String getSubject() {
    return SUBJECT_TEMPLATE.formatted(
        gender == Gender.FEMALE ? "Girokontokundin" : "Girokontokunde",
        capitalizeName(firstName),
        capitalizeName(lastName)
    );
  }

  @Override
  public String getResourcePath() {
    return RESOURCE_PATH;
  }

  public static String capitalizeName(String name) {
    return WordUtils.capitalizeFully(StringUtils.normalizeSpace(name));
  }
}
