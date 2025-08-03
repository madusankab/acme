package com.acme.air.exercise.repo;

import java.util.List;

import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.model.FlightSearchRequest;

public interface FlightInstanceCriteriaAPIRepository {

  List<FlightInstance> searchFlightInstances(FlightSearchRequest request);
}
