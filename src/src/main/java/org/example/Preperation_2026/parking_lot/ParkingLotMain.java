package org.example.Preperation_2026.parking_lot;


import org.example.Preperation_2026.parking_lot.model.*;
import org.example.Preperation_2026.parking_lot.service.ParkingLot;
import org.example.Preperation_2026.parking_lot.strategyImpl.SimpleSpotAllocationStrategy;
import org.example.Preperation_2026.parking_lot.strategyImpl.SpotBasedHourlyPricingStrategy;

public class ParkingLotMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to the Parking Lot Management System!");
        // You can add code here to initialize and run your parking lot system.
        ParkingLot parkingLot = new ParkingLot(new SimpleSpotAllocationStrategy(), new SpotBasedHourlyPricingStrategy());

        parkingLot.addSpot(new ParkingSpot("B1", SpotType.BIKE));
        parkingLot.addSpot(new ParkingSpot("C1", SpotType.COMPACT));
        parkingLot.addSpot(new ParkingSpot("L1", SpotType.LARGE));

        Vehicle bike = new Vehicle("BIKE123", VehicleType.BIKE);

        Ticket ticket = parkingLot.parkVehicle(bike);
        System.out.println("Vehicle parked. Ticket ID: " + ticket.getTicketId() + ", Spot ID: " + ticket.getSpotId());
        Thread.sleep(2000); // Simulate some parking tim   e

        int dueAmount = parkingLot.calculateDue(ticket.getTicketId());
        System.out.println("Due amount for ticket ID " + ticket.getTicketId() + ": " + dueAmount);

        PaymentReceipt receipt = parkingLot.payAndExit(ticket.getTicketId(), PaymentMode.UPI);
        System.out.println("Payment successful. Receipt ID: " + receipt.getReceiptId() + ", Amount Paid: " + receipt.getAmount());

    }
}
