package com.acme.air.exercise.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.air.exercise.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

}
