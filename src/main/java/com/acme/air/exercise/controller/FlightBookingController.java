package com.acme.air.exercise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.air.exercise.model.FlightBookingRequest;
import com.acme.air.exercise.model.FlightBookingResponse;
import com.acme.air.exercise.model.FlightBookingStatus;
import com.acme.air.exercise.service.FlightBookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/v1.0/booking")
@RequiredArgsConstructor
public class FlightBookingController {

  private final FlightBookingService flightBookingService;

  @PostMapping
  public ResponseEntity<FlightBookingResponse> bookFlight(
      @RequestBody @Valid FlightBookingRequest request) {
    FlightBookingResponse response = flightBookingService.bookFlight(request);
    HttpStatus status = response.getStatus() == FlightBookingStatus.SUCCESS ? HttpStatus.CREATED
        : HttpStatus.CONFLICT;
    return ResponseEntity.status(status).body(response);
  }

}
