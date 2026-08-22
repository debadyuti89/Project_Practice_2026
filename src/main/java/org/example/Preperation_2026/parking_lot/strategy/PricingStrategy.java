package org.example.Preperation_2026.parking_lot.strategy;

import org.example.Preperation_2026.parking_lot.model.Ticket;

import java.time.Instant;

public interface PricingStrategy {
    int calculateAmount(Ticket ticket, Instant exitTime);
}
