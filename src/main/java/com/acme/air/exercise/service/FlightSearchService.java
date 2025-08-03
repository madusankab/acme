package com.acme.air.exercise.service;

import com.acme.air.exercise.model.FlightDTO;
import com.acme.air.exercise.model.FlightSearchRequest;
import com.acme.air.exercise.model.FlightSearchResponse;

public interface FlightSearchService {

  FlightDTO findById(String id);

  FlightSearchResponse search(FlightSearchRequest request);

}
