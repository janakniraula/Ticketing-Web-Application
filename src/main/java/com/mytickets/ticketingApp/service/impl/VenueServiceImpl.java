package com.mytickets.ticketingApp.service.impl;

import com.mytickets.ticketingApp.model.Venue;
import com.mytickets.ticketingApp.repository.VenueRepository;
import com.mytickets.ticketingApp.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @Override
    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    @Transactional
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    @Transactional
    public Venue updateVenue(Long id, Venue updatedVenue) {
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found with id: " + id));

        existingVenue.setName(updatedVenue.getName());
        existingVenue.setAddress(updatedVenue.getAddress());
        existingVenue.setCity(updatedVenue.getCity());
        existingVenue.setState(updatedVenue.getState());
        existingVenue.setCountry(updatedVenue.getCountry());
        existingVenue.setCapacity(updatedVenue.getCapacity());
        existingVenue.setVenueMap(updatedVenue.getVenueMap());

        return venueRepository.save(existingVenue);
    }

    @Override
    @Transactional
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }

    @Override
    public List<Venue> findVenuesByCity(String city) {
        return venueRepository.findByCity(city);
    }

    @Override
    public List<Venue> searchVenuesByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }
}