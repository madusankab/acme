package com.acme.air.exercise.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acme.air.exercise.controller.FlightBookingController;
import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.entity.SeatType;
import com.acme.air.exercise.model.FlightBookingRequest;
import com.acme.air.exercise.model.FlightBookingResponse;
import com.acme.air.exercise.model.FlightBookingStatus;
import com.acme.air.exercise.repo.FlightInstanceRepository;
import com.acme.air.exercise.repo.FlightRepository;
import com.acme.air.exercise.service.FlightBookingService;

@SpringBootTest
public class FlightBookingComponentTest {

  private Long flightInstanceId = null;

  @Autowired
  private FlightBookingController flightBookingController;

  @Autowired
  private FlightBookingService flightBookingService;

  @Autowired
  private FlightInstanceRepository flightInstanceRepository;

  @Autowired
  private FlightRepository flightRepository;

  @BeforeEach
  void setup() {
    Flight flight = new Flight();
    flight.setFlightId("FLIGHT_ID");
    Flight persistedFlight = flightRepository.save(flight);

    FlightInstance flightInstance = new FlightInstance();
    flightInstance.setFlight(persistedFlight);

    Seat seat = new Seat();
    seat.setFlightInstance(flightInstance);
    seat.setSeatType(SeatType.ECONOMY_CLASS);
    seat.setBooked(false);
    flightInstance.setSeats(List.of(seat));
    FlightInstance persistedFlightInstance = flightInstanceRepository.save(flightInstance);
    flightInstanceId = persistedFlightInstance.getId();
  }

  @Test
  void testBookFlight_pessimisticWriteLock() throws InterruptedException, ExecutionException {
    FlightBookingRequest flightBookingRequest = getFlightBookingRequest();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    Future<FlightBookingResponse> future1 = executor.submit(() -> {
      return flightBookingService.bookFlight(flightBookingRequest);
    });
    Future<FlightBookingResponse> future2 = executor.submit(() -> {
      Thread.sleep(100);
      return flightBookingService.bookFlight(flightBookingRequest);
    });

    FlightBookingResponse response1 = future1.get();
    FlightBookingResponse response2 = future2.get();

    assertNotNull(response1.getSeat());
    assertNull(response2.getSeat());
    executor.shutdown();
  }

  @Test
  void testBookFlight_whenSeatExistsAndDoesNotExist() {
    FlightBookingRequest flightBookingRequest = getFlightBookingRequest();
    ResponseEntity<FlightBookingResponse> response = flightBookingController.bookFlight(
        flightBookingRequest);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getSeat());
    assertEquals(FlightBookingStatus.SUCCESS, response.getBody().getStatus());

    response = flightBookingController.bookFlight(flightBookingRequest);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNull(response.getBody().getSeat());
    assertEquals(FlightBookingStatus.NO_AVAILABLE_SEATS, response.getBody().getStatus());
  }

  private FlightBookingRequest getFlightBookingRequest() {
    FlightBookingRequest flightBookingRequest = new FlightBookingRequest();
    flightBookingRequest.setFlightInstanceId(flightInstanceId);
    flightBookingRequest.setSeatType(SeatType.ECONOMY_CLASS);
    return flightBookingRequest;
  }

}
