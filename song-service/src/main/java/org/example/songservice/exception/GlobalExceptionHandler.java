package org.example.songservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SongNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleSongNotFoundException(SongNotFoundException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "404");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(SongAlreadyExistsException.class)
  public ResponseEntity<Map<String, Object>> handleSongAlreadyExistsException(SongAlreadyExistsException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "409");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(InvalidIdException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidIdException(InvalidIdException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(InvalidCsvException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidCsvException(InvalidCsvException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("errorMessage", ex.getMessage());
    response.put("errorCode", "400");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, Object> response = new LinkedHashMap<>();
    Map<String, String> details = new TreeMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();

      // If field already has an error, keep the more specific one
      // Prefer Pattern/Size errors over NotNull/NotBlank errors
      if (details.containsKey(fieldName)) {
        String existingMessage = details.get(fieldName);
        // Keep the error that is NOT "required" - prefer validation format errors
        if (existingMessage.contains("is required") && !errorMessage.contains("is required")) {
          details.put(fieldName, errorMessage);
        }
        // Otherwise keep the existing error
      } else {
        details.put(fieldName, errorMessage);
      }
    });

    response.put("errorMessage", "Validation error");
    response.put("errorCode", "400");
    response.put("details", details);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("errorMessage", "An internal server error occurred");
    response.put("errorCode", "500");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}

