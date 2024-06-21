package com.text.engine.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.text.engine.model.Gender;
import org.iban4j.Iban;

import static java.util.Optional.ofNullable;

public class MiscUtils {

  public static String formatAmount(BigDecimal amount) {
    DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.GERMANY);
    symbols.setGroupingSeparator('.');
    return new DecimalFormat("###,###,###,##0.00", symbols).format(amount) + "&nbsp;€";
  }

  public static String formatIban(String iban) {
    iban = iban.replace(" ", "");
    return Iban.valueOf(iban).toFormattedString();
  }

  public static String extractGender(Gender gender) {
    return ofNullable(gender)
        .map(Gender::getGermanSalutation)
        .orElse("");
  }

  public static String extractGreeting(Gender gender) {
    return switch (gender) {
      case MALE -> "geehrter";
      case FEMALE -> "geehrte";
      default -> "geehrte*r";
    };
  }
}
