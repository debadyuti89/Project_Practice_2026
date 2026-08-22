package org.example.Preperation_2026.parking_lot.strategy;


import org.example.Preperation_2026.parking_lot.model.ParkingSpot;
import org.example.Preperation_2026.parking_lot.model.Vehicle;
import org.example.Preperation_2026.parking_lot.service.ParkingLot;

/**
 * Strategy interface for allocating parking spots to vehicles.
 * ParkingLot can have different strategies for allocating spots, such as:
 * - first-come-first-serve
 * - nearest spot
 * - floor-wise
 * - etc.
 */
public interface SpotAllocationStrategy {
    ParkingSpot findSpotForVehicle(Vehicle vehicle, ParkingLot parkingLot);

}
