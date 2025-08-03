package com.acme.air.exercise.exception;

import java.net.InetAddress;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.SneakyThrows;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<RestExceptionResponse> handleResourceNotFoundException(final ResourceNotFoundException exception) {
    return ResponseEntity.ok(toRestExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND));
  }

  @SneakyThrows
  private RestExceptionResponse toRestExceptionResponse(final String message, final HttpStatus status) {
    return RestExceptionResponse.builder()
        .host(InetAddress.getLocalHost().getHostAddress())
        .message(message)
        .httpStatus(status)
        .errorCode(status.value())
        .timeStamp(Instant.now())
        .build();
  }

}
