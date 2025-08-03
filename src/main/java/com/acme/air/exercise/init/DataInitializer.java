package com.acme.air.exercise.init;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.entity.FlightInstanceStatus;
import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.entity.SeatType;
import com.acme.air.exercise.repo.FlightInstanceRepository;
import com.acme.air.exercise.repo.FlightRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private static final Integer NUM_OF_INSTANCES = 5;
  private static final Integer NUM_OF_ECONOMY_CLASS_SEATS = 8;
  private static final Integer NUM_OF_BUSINESS_CLASS_SEATS = 2;
  private static final List<String> seatNumbers = List.of("Seat_1", "Seat_2", "Seat_3", "Seat_4",
      "Seat_5", "Seat_6", "Seat_7", "Seat_8", "Seat_9", "Seat_10");

  private static final String AUCKLAND = "Auckland";
  private static final String WELLINGTON = "Wellington";
  private static final String CHRISTCHURCH = "Christchurch";
  private static final String QUEENSTOWN = "Queenstown";

  private static final String FLIGHT_ID_WELLINGTON = "NZ_1";
  private static final String FLIGHT_ID_CHRISTCHURCH = "NZ_2";
  private static final String FLIGHT_ID_QUEENSTOWN = "NZ_3";

  private final FlightRepository flightRepository;

  private final FlightInstanceRepository flightInstanceRepository;

  @Override
  public void run(String... args) throws Exception {
    populateFlights();
  }

  private void populateFlights() {
    Flight flight1 = new Flight();
    flight1.setFlightId(FLIGHT_ID_WELLINGTON);
    flight1.setOrigin(AUCKLAND);
    flight1.setDestination(WELLINGTON);
    flight1.setDepartureTime(LocalTime.parse("08:00"));
    flight1.setArrivalTime(LocalTime.parse("10:00"));

    Flight flight2 = new Flight();
    flight2.setFlightId(FLIGHT_ID_CHRISTCHURCH);
    flight2.setOrigin(AUCKLAND);
    flight2.setDestination(CHRISTCHURCH);
    flight2.setDepartureTime(LocalTime.parse("09:00"));
    flight2.setArrivalTime(LocalTime.parse("11:00"));

    Flight flight3 = new Flight();
    flight3.setFlightId(FLIGHT_ID_QUEENSTOWN);
    flight3.setOrigin(AUCKLAND);
    flight3.setDestination(QUEENSTOWN);
    flight3.setDepartureTime(LocalTime.parse("10:00"));
    flight3.setArrivalTime(LocalTime.parse("12:00"));

    Flight persistedFlight1 = flightRepository.save(flight1);
    Flight persistedFlight2 = flightRepository.save(flight2);
    Flight persistedFlight3 = flightRepository.save(flight3);

    List<FlightInstance> flightInstances1 = populateFlightInstance(persistedFlight1);
    List<FlightInstance> flightInstances2 = populateFlightInstance(persistedFlight2);
    List<FlightInstance> flightInstances3 = populateFlightInstance(persistedFlight3);

    flightInstanceRepository.saveAll(flightInstances1);
    flightInstanceRepository.saveAll(flightInstances2);
    flightInstanceRepository.saveAll(flightInstances3);
  }

  private List<FlightInstance> populateFlightInstance(final Flight flight) {
    List<FlightInstance> flightInstances = new ArrayList<>();
    IntStream.range(0, NUM_OF_INSTANCES).forEach(i -> {

      List<Seat> seats = populateSeats();
      FlightInstance flightInstance = new FlightInstance();
      flightInstance.setFlight(flight);
      flightInstance.setDepartureDate(LocalDate.now().plusDays(i));
      flightInstance.setDepartureTime(flight.getDepartureTime());
      flightInstance.setArrivalTime(flight.getArrivalTime());
      flightInstance.setSeats(seats);
      flightInstance.setStatus(FlightInstanceStatus.SCHEDULED);

      seats.forEach(seat -> seat.setFlightInstance(flightInstance));
      flightInstances.add(flightInstance);
    });
    return flightInstances;
  }

  private List<Seat> populateSeats() {
    List<Seat> seats = new ArrayList<>();

    IntStream.range(0, NUM_OF_ECONOMY_CLASS_SEATS).forEach(i -> {
      Seat seat = new Seat();
      seat.setSeatNumber(seatNumbers.get(i));
      seat.setSeatType(SeatType.ECONOMY_CLASS);
      seat.setBooked(false);
      seats.add(seat);
    });

    IntStream.range(0, NUM_OF_BUSINESS_CLASS_SEATS).forEach(i -> {
      Seat seat = new Seat();
      seat.setSeatNumber(seatNumbers.get(i));
      seat.setSeatType(SeatType.BUSINESS_CLASS);
      seat.setBooked(false);
      seats.add(seat);
    });

    return seats;
  }

}
