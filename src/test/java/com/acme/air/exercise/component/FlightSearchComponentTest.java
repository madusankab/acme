package com.acme.air.exercise.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.entity.SeatType;
import com.acme.air.exercise.exception.ResourceNotFoundException;
import com.acme.air.exercise.model.FlightDTO;
import com.acme.air.exercise.model.FlightSearchRequest;
import com.acme.air.exercise.model.FlightSearchResponse;
import com.acme.air.exercise.repo.FlightInstanceRepository;
import com.acme.air.exercise.repo.FlightRepository;
import com.acme.air.exercise.service.FlightSearchService;

import jakarta.persistence.EntityManager;

@Transactional
@SpringBootTest
public class FlightSearchComponentTest {

  private static final String FLIGHT_ID = "FL001";

  private static final String ORIGIN = "Auckland";

  private static final String DESTINATION = "Wellington";

  @Autowired
  private FlightSearchService flightSearchService;

  @Autowired
  private FlightRepository flightRepository;

  @Autowired
  private FlightInstanceRepository flightInstanceRepository;

  @Autowired
  private EntityManager entityManager;

  private Flight persistedFlight;

  private FlightInstance persistedFlightInstance;

  @BeforeEach
  void setUp() {
    Flight flight = new Flight();
    flight.setFlightId(FLIGHT_ID);
    flight.setOrigin(ORIGIN);
    flight.setDestination(DESTINATION);
    flight.setDepartureTime(LocalTime.parse("10:00"));
    flight.setArrivalTime(LocalTime.parse("12:00"));
    persistedFlight = flightRepository.save(flight);

    FlightInstance flightInstance = new FlightInstance();
    flightInstance.setDepartureDate(LocalDate.now().plusDays(1));
    flightInstance.setFlight(persistedFlight);

    Seat seat = new Seat();
    seat.setFlightInstance(flightInstance);
    seat.setSeatType(SeatType.ECONOMY_CLASS);
    seat.setBooked(false);
    flightInstance.setSeats(List.of(seat));
    persistedFlightInstance = flightInstanceRepository.save(flightInstance);

    entityManager.flush();
    entityManager.clear();
  }

  @Test
  void testFindById_whenFlightExists() {
    FlightDTO dto = flightSearchService.findById(persistedFlight.getFlightId());

    assertNotNull(dto);
    assertEquals(FLIGHT_ID, dto.getFlightId());
    assertEquals(ORIGIN, dto.getOrigin());
    assertEquals(DESTINATION, dto.getDestination());
    assertEquals(1, dto.getFlightInstances().size());
    assertEquals(persistedFlightInstance.getId(), dto.getFlightInstances().getFirst().getId());
  }

  @Test
  void testFindById_whenFlightNotFound_throwsException() {
    String invalidId = "INVALID123";
    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
        flightSearchService.findById(invalidId));

    assertEquals("Flight not found for the id: " + invalidId, exception.getMessage());
  }

  @Test
  void testSearch_returnsMatchingFlightInstances() {
    FlightSearchRequest request = new FlightSearchRequest();
    request.setOrigin(ORIGIN);
    request.setDestination(DESTINATION);
    request.setDepartureDate(LocalDate.now().plusDays(1));
    request.setNumOfSeats(1);

    FlightSearchResponse response = flightSearchService.search(request);
    assertNotNull(response);
    assertFalse(response.getFlightInstances().isEmpty());
  }

  @Test
  void testSearch_withNoMatch_returnsEmptyList() {
    FlightSearchRequest request = new FlightSearchRequest();
    request.setOrigin(ORIGIN);
    request.setDestination(DESTINATION);
    request.setDepartureDate(LocalDate.now().plusDays(100));
    request.setNumOfSeats(1);

    FlightSearchResponse response = flightSearchService.search(request);
    assertNotNull(response);
    assertTrue(response.getFlightInstances().isEmpty());
  }
}
