package com.mytickets.ticketingApp.service;

import com.mytickets.ticketingApp.model.Event;
import com.mytickets.ticketingApp.model.EventStatus;
import com.mytickets.ticketingApp.model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents();
    Page<Event> getEventsPage(Pageable pageable);
    Optional<Event> getEventById(Long id);
    Event createEvent(Event event, Long creatorId);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
    List<Event> findEventsByCreator(Long creatorId);
    List<Event> findEventsByType(EventType eventType);
    List<Event> findEventsByStatus(EventStatus status);
    List<Event> findUpcomingEvents();
    List<Event> findEventsByVenue(Long venueId);
    List<Event> searchEventsByName(String name);
    List<Event> findUpcomingEventsWithAvailableTickets();
    List<Event> findUpcomingEventsByCity(String city);
}