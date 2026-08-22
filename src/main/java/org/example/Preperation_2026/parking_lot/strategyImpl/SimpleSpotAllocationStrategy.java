package org.example.Preperation_2026.parking_lot.strategyImpl;


import org.example.Preperation_2026.parking_lot.model.ParkingSpot;
import org.example.Preperation_2026.parking_lot.model.SpotType;
import org.example.Preperation_2026.parking_lot.model.Vehicle;
import org.example.Preperation_2026.parking_lot.model.VehicleType;
import org.example.Preperation_2026.parking_lot.service.ParkingLot;
import org.example.Preperation_2026.parking_lot.strategy.SpotAllocationStrategy;

public class SimpleSpotAllocationStrategy implements SpotAllocationStrategy {


    @Override
    public ParkingSpot findSpotForVehicle(Vehicle vehicle, ParkingLot parkingLot) {
        SpotType required = requiredSpot(vehicle.getVehicleType());
        for (ParkingSpot spot : parkingLot.getAllSpots()) {
            if(spot.isFree() && spot.getSpotType() == required) {
                return spot;
            }
        }
        return null;
    }

    private SpotType requiredSpot(VehicleType vehicleType) {
        return switch (vehicleType) {
            case BIKE -> SpotType.BIKE;
            case CAR -> SpotType.COMPACT;
            case TRUCK -> SpotType.LARGE;
        };
    }
}
