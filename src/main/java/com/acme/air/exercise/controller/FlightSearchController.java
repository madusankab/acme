package com.acme.air.exercise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.air.exercise.model.FlightDTO;
import com.acme.air.exercise.model.FlightSearchRequest;
import com.acme.air.exercise.model.FlightSearchResponse;
import com.acme.air.exercise.service.FlightSearchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/v1.0/flight")
@RequiredArgsConstructor
public class FlightSearchController {

  private final FlightSearchService flightSearchService;

  @GetMapping("/{id}")
  public ResponseEntity<FlightDTO> findById(@PathVariable final String id) {
    return ResponseEntity.ok(flightSearchService.findById(id));
  }

  @GetMapping("/search")
  public ResponseEntity<FlightSearchResponse> search(
      @ModelAttribute @Valid final FlightSearchRequest request) {
    return ResponseEntity.ok(flightSearchService.search(request));
  }

}
