package com.mytickets.ticketingApp.repository;

import com.mytickets.ticketingApp.model.Event;
import com.mytickets.ticketingApp.model.PricingTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingTierRepository extends JpaRepository<PricingTier, Long> {
    List<PricingTier> findByEvent(Event event);

    List<PricingTier> findByEventId(Long eventId);

    List<PricingTier> findByEventAndAvailableGreaterThan(Event event, Integer minAvailable);
}