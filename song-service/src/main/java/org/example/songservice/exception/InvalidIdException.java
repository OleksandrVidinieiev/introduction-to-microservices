package org.example.songservice.exception;

public class InvalidIdException extends RuntimeException {
  public InvalidIdException(String message) {
    super(message);
  }
}

