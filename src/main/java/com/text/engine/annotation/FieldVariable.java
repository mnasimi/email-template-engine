package com.text.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(FieldVariable.List.class)
public @interface FieldVariable {

  /**
   * @return - Name of the email template variable
   */
  String placeHolder() default "";

  /**
   * Optional parameter, specifying that the variable value requires special formatting,
   * by default the value is inserted as it is
   * @return - type of formatting
   */
  FieldVariableFormat formatter() default FieldVariableFormat.IDENTITY;

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface List {
    FieldVariable[] value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface Exclude {}
}
