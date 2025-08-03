package com.acme.air.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.exception.ResourceNotFoundException;
import com.acme.air.exercise.mapper.FlightDTOMapper;
import com.acme.air.exercise.mapper.FlightInstanceDTOMapper;
import com.acme.air.exercise.model.FlightDTO;
import com.acme.air.exercise.model.FlightInstanceDTO;
import com.acme.air.exercise.model.FlightSearchRequest;
import com.acme.air.exercise.model.FlightSearchResponse;
import com.acme.air.exercise.repo.FlightInstanceCriteriaAPIRepository;
import com.acme.air.exercise.repo.FlightInstanceRepository;
import com.acme.air.exercise.repo.FlightRepository;
import com.acme.air.exercise.service.impl.FlightSearchServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FlightSearchServiceTest {

  @Mock
  private FlightRepository flightRepository;

  @Mock
  private FlightInstanceRepository flightInstanceRepository;

  @Mock
  private FlightInstanceCriteriaAPIRepository flightInstanceCriteriaAPIRepository;

  @Mock
  private FlightDTOMapper flightDTOMapper;

  @Mock
  private FlightInstanceDTOMapper flightInstanceDTOMapper;

  @InjectMocks
  private FlightSearchServiceImpl flightSearchService;

  @Test
  void testFindById_whenFlightExists() {
    String flightId = "F001";
    Flight flight = new Flight();
    flight.setFlightId(flightId);

    FlightDTO flightDTO = new FlightDTO();
    flightDTO.setFlightId(flightId);

    List<FlightInstance> flightInstances = List.of(new FlightInstance());
    List<FlightInstanceDTO> flightInstanceDTOs = List.of(new FlightInstanceDTO());

    when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
    when(flightDTOMapper.map(flight)).thenReturn(flightDTO);
    when(flightInstanceRepository.findByFlight(flight)).thenReturn(flightInstances);
    when(flightInstanceDTOMapper.map(flightInstances)).thenReturn(flightInstanceDTOs);

    FlightDTO result = flightSearchService.findById(flightId);

    assertNotNull(result);
    assertEquals(flightId, result.getFlightId());
    verify(flightRepository).findById(flightId);
    verify(flightDTOMapper).map(flight);
    verify(flightInstanceRepository).findByFlight(flight);
    verify(flightInstanceDTOMapper).map(flightInstances);
  }

  @Test
  void testFindById_whenFlightDoesNotExist() {
    String flightId = "F002";
    when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> flightSearchService.findById(flightId)
    );

    assertEquals("Flight not found for the id: " + flightId, exception.getMessage());
    verify(flightRepository).findById(flightId);
    verifyNoInteractions(flightDTOMapper);
    verifyNoInteractions(flightInstanceRepository);
    verifyNoInteractions(flightInstanceDTOMapper);
  }

  @Test
  void testSearch_success() {
    FlightSearchRequest request = new FlightSearchRequest();
    List<FlightInstance> instances = List.of(new FlightInstance());
    List<FlightInstanceDTO> instanceDTOs = List.of(new FlightInstanceDTO());

    when(flightInstanceCriteriaAPIRepository.searchFlightInstances(request)).thenReturn(instances);
    when(flightInstanceDTOMapper.map(instances)).thenReturn(instanceDTOs);

    FlightSearchResponse result = flightSearchService.search(request);

    assertNotNull(result);
    assertEquals(instanceDTOs, result.getFlightInstances());
    verify(flightInstanceCriteriaAPIRepository).searchFlightInstances(request);
    verify(flightInstanceDTOMapper).map(instances);
  }

}
