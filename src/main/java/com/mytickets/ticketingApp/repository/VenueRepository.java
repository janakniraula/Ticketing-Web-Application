package com.mytickets.ticketingApp.repository;

import com.mytickets.ticketingApp.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByCity(String city);
    List<Venue> findByNameContainingIgnoreCase(String name);
}