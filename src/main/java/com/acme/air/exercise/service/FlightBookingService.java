package com.acme.air.exercise.service;

import com.acme.air.exercise.model.FlightBookingRequest;
import com.acme.air.exercise.model.FlightBookingResponse;

public interface FlightBookingService {

  FlightBookingResponse bookFlight(FlightBookingRequest request);

}
