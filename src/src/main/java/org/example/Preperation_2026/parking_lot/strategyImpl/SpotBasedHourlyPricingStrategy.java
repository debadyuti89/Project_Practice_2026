package org.example.Preperation_2026.parking_lot.strategyImpl;


import org.example.Preperation_2026.parking_lot.model.Ticket;
import org.example.Preperation_2026.parking_lot.strategy.PricingStrategy;

import java.time.Duration;
import java.time.Instant;

public class SpotBasedHourlyPricingStrategy implements PricingStrategy {
    @Override
    public int calculateAmount(Ticket ticket, Instant exitTime) {
        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();

        long hours = (minutes + 59) / 60; // Round up to the next hour

        int ratePerHour = switch (ticket.getAssignedSpotType()) {
            case BIKE -> 10;
            case COMPACT -> 20;
            case LARGE -> 50;
        };

        return (int) (hours * ratePerHour);
    }
}
