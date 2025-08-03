package com.acme.air.exercise.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;

@Repository
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {

  List<FlightInstance> findByFlight(Flight flight);
}
