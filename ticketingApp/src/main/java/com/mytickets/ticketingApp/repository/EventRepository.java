package com.mytickets.ticketingApp.repository;

import com.mytickets.ticketingApp.model.Event;
import com.mytickets.ticketingApp.model.EventStatus;
import com.mytickets.ticketingApp.model.EventType;
import com.mytickets.ticketingApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCreator(User creator);

    List<Event> findByEventType(EventType eventType);

    List<Event> findByStatus(EventStatus status);

    List<Event> findByEventDateAfter(LocalDateTime date);

    List<Event> findByNameContainingIgnoreCase(String name);

    List<Event> findByVenueId(Long venueId);

    @Query("SELECT e FROM Event e WHERE e.eventDate > ?1 AND e.availableTickets > 0")
    List<Event> findUpcomingEventsWithAvailableTickets(LocalDateTime currentDate);

    @Query("SELECT e FROM Event e JOIN e.venue v WHERE v.city = ?1 AND e.eventDate > ?2")
    List<Event> findUpcomingEventsByCity(String city, LocalDateTime currentDate);

    Page<Event> findAll(Pageable pageable);
}