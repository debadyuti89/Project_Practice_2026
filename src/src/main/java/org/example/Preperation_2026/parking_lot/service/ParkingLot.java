package org.example.Preperation_2026.parking_lot.service;



import org.example.Preperation_2026.parking_lot.model.*;
import org.example.Preperation_2026.parking_lot.strategy.PricingStrategy;
import org.example.Preperation_2026.parking_lot.strategy.SpotAllocationStrategy;

import java.time.Instant;
import java.util.*;

public class ParkingLot {
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();
    private final Map<String, Ticket> tickets = new HashMap<>();
    private final Map<String, ParkingSpot> spotsMap = new HashMap<>();
    private final SpotAllocationStrategy spotAllocationStrategy;
    private final PricingStrategy pricingStrategy;

    public ParkingLot(SpotAllocationStrategy spotAllocationStrategy, PricingStrategy pricingStrategy) {
        this.spotAllocationStrategy = spotAllocationStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public void addSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
    }

    public List<ParkingSpot> getAllSpots() {
        return Collections.unmodifiableList(parkingSpots);
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = spotAllocationStrategy.findSpotForVehicle(vehicle, this);
        if (spot == null) {
            throw new IllegalStateException("No available spot for vehicle type: " + vehicle.getVehicleType());
        }
        spot.parkVehicle(vehicle);

        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, vehicle, spot);
        tickets.put(ticketId, ticket);
        return ticket;
    }

    public int calculateDue(String ticketId) {
        Ticket ticket = getActiveTicket(ticketId);
        return pricingStrategy.calculateAmount(ticket, Instant.now());
    }

    private Ticket getActiveTicket(String ticketId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalStateException("Invalid Ticket!");
        }
        if (ticket.getTicketStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket is not Active.");
        }
        return ticket;
    }

    public PaymentReceipt payAndExit(String ticketId, PaymentMode paymentMode) {
        Ticket ticket = getActiveTicket(ticketId);
        int amount = pricingStrategy.calculateAmount(ticket, Instant.now());

        // Assume that payment success
        PaymentReceipt receipt = new PaymentReceipt(UUID.randomUUID().toString(), amount, PaymentStatus.SUCCESS);

        // Free the spot
        ParkingSpot spot = findSpotById(ticket.getSpotId());
        spot.unparkVehicle();
        ticket.setTicketStatus(TicketStatus.PAID);
        return receipt;
    }

    private ParkingSpot findSpotById(String spotId) {
        return spotsMap.get(spotId);
    }
}
