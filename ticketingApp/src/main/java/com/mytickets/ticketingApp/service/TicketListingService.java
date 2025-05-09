package com.mytickets.ticketingApp.service;

import com.mytickets.ticketingApp.model.TicketListing;

import java.util.List;
import java.util.Optional;

public interface TicketListingService {
    List<TicketListing> getAllListings();
    Optional<TicketListing> getListingById(Long id);
    List<TicketListing> getListingsBySeller(Long sellerId);
    List<TicketListing> getActiveListingsByEvent(Long eventId);
    List<TicketListing> getActiveListingsByEventOrderByPrice(Long eventId);
    TicketListing createListing(TicketListing listing, Long ticketId, Long sellerId);
    TicketListing updateListing(Long id, TicketListing listing);
    void cancelListing(Long id);
    TicketListing purchaseListing(Long listingId, Long buyerId);
    Long countActiveListingsByEvent(Long eventId);
}