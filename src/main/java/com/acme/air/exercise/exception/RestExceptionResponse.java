package com.acme.air.exercise.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RestExceptionResponse {

  private String host;

  private int errorCode;

  private HttpStatus httpStatus;

  private Instant timeStamp;

  private String message;
}
