// src/main/java/com/mytickets/ticketingApp/repository/TicketListingRepository.java
package com.mytickets.ticketingApp.repository;

import com.mytickets.ticketingApp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketListingRepository extends JpaRepository<TicketListing, Long> {
    List<TicketListing> findBySeller(User seller);

    List<TicketListing> findBySellerId(Long sellerId);

    List<TicketListing> findByStatus(ListingStatus status);

    Optional<TicketListing> findByTicketId(Long ticketId);

    @Query("SELECT tl FROM TicketListing tl JOIN tl.ticket t JOIN t.event e WHERE e.id = ?1 AND tl.status = 'ACTIVE'")
    List<TicketListing> findActiveListingsByEvent(Long eventId);

    @Query("SELECT tl FROM TicketListing tl JOIN tl.ticket t JOIN t.event e WHERE e.id = ?1 AND tl.status = 'ACTIVE' ORDER BY tl.askingPrice ASC")
    List<TicketListing> findActiveListingsByEventOrderByPriceAsc(Long eventId);

    @Query("SELECT COUNT(tl) FROM TicketListing tl JOIN tl.ticket t WHERE t.event.id = ?1 AND tl.status = 'ACTIVE'")
    Long countActiveListingsByEvent(Long eventId);
}