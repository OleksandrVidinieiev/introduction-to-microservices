package org.example.resourceservice.util;

import java.util.ArrayList;
import java.util.List;

import org.example.resourceservice.exception.InvalidCsvException;
import org.example.resourceservice.exception.InvalidIdException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationHelper {

  public static Long parseAndValidateId(String id) {
    try {
      long parsedId = Long.parseLong(id);
      if (parsedId <= 0) {
        throw new InvalidIdException("Invalid value '" + id + "' for ID. Must be a positive integer");
      }
      return parsedId;
    } catch (NumberFormatException e) {
      throw new InvalidIdException("Invalid value '" + id + "' for ID. Must be a positive integer");
    }
  }

  public static List<Long> parseAndValidateCsv(String csv) {
    if (csv == null || csv.isEmpty()) {
      throw new InvalidCsvException("CSV string cannot be empty");
    }

    if (csv.length() >= 200) {
      throw new InvalidCsvException("CSV string is too long: received " + csv.length() + " characters, maximum allowed is 200");
    }

    String[] parts = csv.split(",");
    List<Long> ids = new ArrayList<>();

    for (String part : parts) {
      String trimmed = part.trim();
      if (!trimmed.isEmpty()) {
        try {
          long id = Long.parseLong(trimmed);
          if (id <= 0) {
            throw new InvalidCsvException("Invalid ID format: '" + trimmed + "'. Only positive integers are allowed");
          }
          ids.add(id);
        } catch (NumberFormatException e) {
          throw new InvalidCsvException("Invalid ID format: '" + trimmed + "'. Only positive integers are allowed");
        }
      }
    }

    return ids;
  }
}
