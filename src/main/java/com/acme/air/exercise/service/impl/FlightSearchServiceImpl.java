package com.acme.air.exercise.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.exception.ResourceNotFoundException;
import com.acme.air.exercise.mapper.FlightDTOMapper;
import com.acme.air.exercise.mapper.FlightInstanceDTOMapper;
import com.acme.air.exercise.model.FlightDTO;
import com.acme.air.exercise.model.FlightSearchRequest;
import com.acme.air.exercise.model.FlightSearchResponse;
import com.acme.air.exercise.repo.FlightInstanceCriteriaAPIRepository;
import com.acme.air.exercise.repo.FlightInstanceRepository;
import com.acme.air.exercise.repo.FlightRepository;
import com.acme.air.exercise.service.FlightSearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {

  private final FlightRepository flightRepository;

  private final FlightInstanceRepository flightInstanceRepository;

  private final FlightInstanceCriteriaAPIRepository flightInstanceCriteriaAPIRepository;

  private final FlightDTOMapper flightDTOMapper;

  private final FlightInstanceDTOMapper flightInstanceDTOMapper;

  @Override
  public FlightDTO findById(final String id) {
    Flight flight = flightRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Flight not found for the id: " + id));

    List<FlightInstance> flightInstances = flightInstanceRepository.findByFlight(flight);
    FlightDTO dto = flightDTOMapper.map(flight);
    dto.setFlightInstances(flightInstanceDTOMapper.map(flightInstances));
    return dto;
  }

  @Override
  public FlightSearchResponse search(final FlightSearchRequest request) {
    List<FlightInstance> flightInstances = flightInstanceCriteriaAPIRepository.searchFlightInstances(
        request);
    return FlightSearchResponse.builder()
        .flightInstances(flightInstanceDTOMapper.map(flightInstances)).build();
  }
}
