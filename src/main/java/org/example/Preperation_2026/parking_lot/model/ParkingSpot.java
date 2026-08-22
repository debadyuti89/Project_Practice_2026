package org.example.Preperation_2026.parking_lot.model;

public class ParkingSpot {
    private final String id;
    private final SpotType spotType;

    private Vehicle parkedVehicle;

    public ParkingSpot(String id, SpotType spotType) {
        this.id = id;
        this.spotType = spotType;
    }

    public String getId() {
        return id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public boolean isFree() {
        return parkedVehicle == null;
    }

    public void parkVehicle(Vehicle vehicle) {
        if (!isFree()) {
            throw new IllegalStateException("Parking spot "+ id +"is already occupied.");
        }
        this.parkedVehicle = vehicle;
    }

    public Vehicle unparkVehicle() {
        if (isFree()) {
            throw new IllegalStateException("Parking spot "+ id +"is already free.");
        }
        Vehicle vehicle = this.parkedVehicle;
        this.parkedVehicle = null;
        return vehicle;
    }
}
