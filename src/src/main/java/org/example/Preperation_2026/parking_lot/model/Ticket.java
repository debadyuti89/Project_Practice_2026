package org.example.Preperation_2026.parking_lot.model;

import java.time.Instant;

public class Ticket {
    private final String ticketId;
    private final String vehiclePlate;
    private final VehicleType vehicleType;
    private final String spotId;
    private final SpotType assignedSpotType;
    private final Instant entryTime;
    private TicketStatus ticketStatus;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketId = ticketId;
        this.vehiclePlate = vehicle.getLicensePlate();
        this.vehicleType = vehicle.getVehicleType();
        this.spotId = parkingSpot.getId();
        this.assignedSpotType = parkingSpot.getSpotType();
        this.entryTime = Instant.now();
        this.ticketStatus = TicketStatus.ACTIVE;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotType getAssignedSpotType() {
        return assignedSpotType;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
