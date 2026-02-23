package org.example.resourceservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "404");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(InvalidMp3Exception.class)
  public ResponseEntity<Map<String, String>> handleInvalidMp3Exception(InvalidMp3Exception ex) {
    Map<String, String> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(InvalidIdException.class)
  public ResponseEntity<Map<String, String>> handleInvalidIdException(InvalidIdException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(InvalidCsvException.class)
  public ResponseEntity<Map<String, String>> handleInvalidCsvException(InvalidCsvException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<Map<String, String>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
    Map<String, String> response = new HashMap<>();
    String contentType = ex.getContentType() != null ? ex.getContentType().toString() : "unknown";
    String errorMessage = "Invalid file format: " + contentType + ". Only MP3 files are allowed";
    response.put("errorMessage", errorMessage);
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("errorMessage", "The request body is invalid MP3");
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    Map<String, String> response = new HashMap<>();
    response.put("errorMessage", "An internal server error occurred");
    response.put("errorCode", "500");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}

