package com.mytickets.ticketingApp.controller;

import com.mytickets.ticketingApp.model.TicketListing;
import com.mytickets.ticketingApp.security.services.UserDetailsImpl;
import com.mytickets.ticketingApp.service.TicketListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/listings")
public class TicketListingController {

    @Autowired
    private TicketListingService ticketListingService;

    @GetMapping
    public ResponseEntity<List<TicketListing>> getAllListings() {
        List<TicketListing> listings = ticketListingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketListing> getListingById(@PathVariable Long id) {
        return ticketListingService.getListingById(id)
                .map(listing -> new ResponseEntity<>(listing, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/my-listings")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TicketListing>> getMyListings() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<TicketListing> listings = ticketListingService.getListingsBySeller(userDetails.getId());
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<TicketListing>> getListingsByEvent(@PathVariable Long eventId) {
        List<TicketListing> listings = ticketListingService.getActiveListingsByEvent(eventId);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/event/{eventId}/cheapest")
    public ResponseEntity<List<TicketListing>> getCheapestListingsByEvent(@PathVariable Long eventId) {
        List<TicketListing> listings = ticketListingService.getActiveListingsByEventOrderByPrice(eventId);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/event/{eventId}/count")
    public ResponseEntity<Map<String, Long>> countListingsByEvent(@PathVariable Long eventId) {
        Long count = ticketListingService.countActiveListingsByEvent(eventId);

        Map<String, Long> response = new HashMap<>();
        response.put("count", count);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TicketListing> createListing(
            @Valid @RequestBody TicketListing listing,
            @RequestParam Long ticketId) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        TicketListing newListing = ticketListingService.createListing(listing, ticketId, userDetails.getId());
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TicketListing> updateListing(
            @PathVariable Long id,
            @Valid @RequestBody TicketListing listing) {

        // Add seller verification logic here if needed
        TicketListing updatedListing = ticketListingService.updateListing(id, listing);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> cancelListing(@PathVariable Long id) {
        // Add seller verification logic here if needed
        ticketListingService.cancelListing(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TicketListing> purchaseListing(@PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        TicketListing purchasedListing = ticketListingService.purchaseListing(id, userDetails.getId());
        return new ResponseEntity<>(purchasedListing, HttpStatus.OK);
    }
}