package org.example.songservice.exception;

public class InvalidCsvException extends RuntimeException {
  public InvalidCsvException(String message) {
    super(message);
  }
}

