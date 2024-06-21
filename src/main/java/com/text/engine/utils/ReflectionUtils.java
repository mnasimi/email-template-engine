package com.text.engine.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.Objects.nonNull;

public class ReflectionUtils {

  public static List<Field> discoverFields(Class<?> clazz) {
    var discoveredFields = new ArrayList<Field>();
    var currentClass = clazz;
    while (nonNull(currentClass)) {
      for (Field field : currentClass.getDeclaredFields()) {
        if (!isStatic(field.getModifiers())) {
          discoveredFields.add(field);
        }
      }
      currentClass = currentClass.getSuperclass();
    }
    return discoveredFields;
  }

  public static Object getFieldValue(Field field, Object declaringObject)
      throws InvocationTargetException, IllegalAccessException {
    var getterTemplate = !field.getType().equals(Boolean.TYPE) ? "get%s" : "is%s";
    var fieldName = field.getName();
    var getterName = getterTemplate.formatted(StringUtils.capitalize(fieldName));
    var fieldGetter = Arrays.stream(declaringObject.getClass().getMethods())
        .filter(method -> getterName.equals(method.getName()))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("Could not find getter for the object field: %s"
                .formatted(fieldName))
        );
    return fieldGetter.invoke(declaringObject);
  }

}
