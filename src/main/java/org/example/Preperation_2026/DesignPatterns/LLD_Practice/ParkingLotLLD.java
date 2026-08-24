package org.example.Preperation_2026.DesignPatterns.LLD_Practice;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.time.*;

enum SpotType {REGULAR, COMPACT, LARGE, HANDICAPPED}

enum VehicleType {CAR, MOTORCYCLE, BUS}

enum CustomerTier {REGULAR, PREMIUM, VIP}

enum CryptoCurrency {BTC, ETH, SOL}

enum SystemEventType {ENTRY_RECORDED, SPOT_RELEASED, SURGE_MULTIPLIER_CHANGED}

// =========================================================================
// EVENT-DRIVEN COMMUNICATION BUS
// =========================================================================
class EventMessage {
    private final SystemEventType type;
    private final String contextData;
    private final Instant timestamp;

    public EventMessage(SystemEventType type, String contextData) {
        this.type = type;
        this.contextData = contextData;
        this.timestamp = Instant.now();
    }

    public SystemEventType getType() {
        return type;
    }

    public String getContextData() {
        return contextData;
    }
}

class ParkingEventBus {
    private final BlockingQueue<EventMessage> eventQueue = new LinkedBlockingQueue<>();
    private final ExecutorService workerPool = Executors.newFixedThreadPool(2);
    private final List<java.util.function.Consumer<EventMessage>> subscribers = new CopyOnWriteArrayList<>();
    private volatile boolean running = true;

    public ParkingEventBus() {
        workerPool.execute(this::processEventsLoop);
    }

    public void registerSubscriber(java.util.function.Consumer<EventMessage> sub) {
        subscribers.add(sub);
    }

    public void publish(EventMessage msg) {
        eventQueue.offer(msg);
    }

    private void processEventsLoop() {
        while (running || !eventQueue.isEmpty()) {
            try {
                EventMessage event = eventQueue.poll(500, TimeUnit.MILLISECONDS);
                if (event != null) {
                    for (java.util.function.Consumer<EventMessage> sub : subscribers) {
                        sub.accept(event);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void shutdown() {
        this.running = false;
        workerPool.shutdown();
    }
}

// =========================================================================
// LIVE SURGE VOLATILITY PUBLISHER
// =========================================================================
class SurgeVolatilityPublisher {
    private final ParkingEventBus bus;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final java.util.function.DoubleSupplier occupancySupplier;

    public SurgeVolatilityPublisher(ParkingEventBus bus, java.util.function.DoubleSupplier occupancySupplier) {
        this.bus = bus;
        this.occupancySupplier = occupancySupplier;
    }

    public void startMonitoring() {
        scheduler.scheduleAtFixedRate(() -> {
            double currentLoad = occupancySupplier.getAsDouble();
            double dynamicMultiplier = 1.0;
            if (currentLoad > 0.8) dynamicMultiplier = 2.5;
            else if (currentLoad > 0.5) dynamicMultiplier = 1.5;

            bus.publish(new EventMessage(SystemEventType.SURGE_MULTIPLIER_CHANGED,
                    String.format("LOAD: %.2f | MULTIPLIER: %.1fx", currentLoad, dynamicMultiplier)));
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stopMonitoring() {
        scheduler.shutdown();
    }
}

// =========================================================================
// ADVANCED MECHANICAL GARAGE ELEVATOR STACKER CONTROL
// =========================================================================
class ElevatorStackerControl {
    private final ReentrantLock liftLock = new ReentrantLock();
    private final Condition liftAvailable = liftLock.newCondition();
    private final int totalLiftBays;
    private int activeLiftsInUse = 0;

    public ElevatorStackerControl(int totalLiftBays) {
        this.totalLiftBays = totalLiftBays;
    }

    public void executeMechanicalStacking(String ticketNumber, String targetFloor) {
        liftLock.lock();
        try {
            while (activeLiftsInUse >= totalLiftBays) {
                System.out.printf("[ELEVATOR SYSTEM] Lift bays full. Stacking request for Ticket %s deferred.\n", ticketNumber);
                liftAvailable.await();
            }
            activeLiftsInUse++;
            System.out.printf("[ELEVATOR SYSTEM] Lift bay locked. Moving Ticket %s vertically to %s.\n", ticketNumber, targetFloor);

            // Simulating short mechanical transit window
            Thread.sleep(100);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            activeLiftsInUse--;
            liftAvailable.signalAll();
            liftLock.unlock();
            System.out.printf("[ELEVATOR SYSTEM] Lift bay freed from Ticket %s stacking routine.\n", ticketNumber);
        }
    }
}

// =========================================================================
// STRATEGY PATTERN CRYPTO GATEWAY
// =========================================================================
interface CryptoPaymentStrategy {
    void processPayment(double usdAmount, String walletAddress);
}

class BitcoinPaymentService implements CryptoPaymentStrategy {
    private final double btcToUsdExchangeRate = 95000.0;

    @Override
    public void processPayment(double usdAmount, String walletAddress) {
        System.out.printf("[PAYMENT GATEWAY] Settled via BTC. Wallet: %s. Cost: %.6f BTC ($%.2f USD)\n",
                walletAddress, (usdAmount / btcToUsdExchangeRate), usdAmount);
    }
}

class SolanaPaymentService implements CryptoPaymentStrategy {
    private final double solToUsdExchangeRate = 185.0;

    @Override
    public void processPayment(double usdAmount, String walletAddress) {
        System.out.printf("[PAYMENT GATEWAY] Settled via SOL. Wallet: %s. Cost: %.4f SOL ($%.2f USD)\n",
                walletAddress, (usdAmount / solToUsdExchangeRate), usdAmount);
    }
}

class PaymentContext {
    private final Map<CryptoCurrency, CryptoPaymentStrategy> strategies = new ConcurrentHashMap<>();

    public PaymentContext() {
        strategies.put(CryptoCurrency.BTC, new BitcoinPaymentService());
        strategies.put(CryptoCurrency.SOL, new SolanaPaymentService());
    }

    public void executeCryptoSettlement(CryptoCurrency coin, double usdAmount, String walletAddress) {
        CryptoPaymentStrategy strategy = strategies.get(coin);
        if (strategy != null) {
            strategy.processPayment(usdAmount, walletAddress);
        }
    }
}

// =========================================================================
// INFRASTRUCTURE MANAGEMENT STRUCTURES
// =========================================================================
class ParkingSpot {
    private final String spotNumber;
    private final SpotType type;
    private final String floorId;
    private boolean isOccupied = false;

    public ParkingSpot(String spotNumber, SpotType type, String floorId) {
        this.spotNumber = spotNumber;
        this.type = type;
        this.floorId = floorId;
    }

    public synchronized boolean isAvailable() {
        return !isOccupied;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public String getFloorId() {
        return floorId;
    }

    public boolean canFit(VehicleType vType) {
        return switch (vType) {
            case MOTORCYCLE -> (type == SpotType.COMPACT || type == SpotType.REGULAR);
            case CAR -> (type == SpotType.REGULAR || type == SpotType.LARGE);
            case BUS -> (type == SpotType.LARGE);
        };
    }

    public synchronized boolean park() {
        if (isOccupied) return false;
        this.isOccupied = true;
        return true;
    }

    public synchronized void free() {
        isOccupied = false;
    }
}

class ParkingLot {
    private final Map<String, List<ParkingSpot>> floors = new ConcurrentHashMap<>();
    private final AtomicInteger totalOccupiedSpots = new AtomicInteger(0);
    private final int totalCapacity;

    public ParkingLot(int numFloors, Map<SpotType, Integer> spotsPerType) {
        int capacitySum = 0;
        for (int i = 0; i < numFloors; i++) {
            String floorId = "F" + (i + 1);
            List<ParkingSpot> floorSpots = new CopyOnWriteArrayList<>();
            for (Map.Entry<SpotType, Integer> entry : spotsPerType.entrySet()) {
                for (int j = 0; j < entry.getValue(); j++) {
                    floorSpots.add(new ParkingSpot(floorId + "-" + entry.getKey() + "-" + (j + 1), entry.getKey(), floorId));
                }
            }
            floors.put(floorId, floorSpots);
        }
        for (Integer count : spotsPerType.values()) {
            capacitySum += count;
        }
        this.totalCapacity = capacitySum * numFloors;
    }

    public ParkingSpot findAndReserveSpot(VehicleType type) {
        for (List<ParkingSpot> floorSpots : floors.values()) {
            for (ParkingSpot spot : floorSpots) {
                if (spot.canFit(type) && spot.park()) {
                    totalOccupiedSpots.incrementAndGet();
                    return spot;
                }
            }
        }
        return null;
    }

    public void releaseSpot(ParkingSpot spot) {
        spot.free();
        totalOccupiedSpots.decrementAndGet();
    }

    public double getOccupancyRate() {
        return (double) totalOccupiedSpots.get() / totalCapacity;
    }

    public void displayStatus() {
        floors.forEach((floorId, spots) -> {
            long free = spots.stream().filter(ParkingSpot::isAvailable).count();
            System.out.printf("  Floor %s -> Available Spots: %d/%d\n", floorId, free, spots.size());
        });
    }
}

class ParkingTicket {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private final String ticketNumber;
    private final VehicleType vehicleType;
    private final ParkingSpot spot;
    private long simulatedMinutes = 0;

    public ParkingTicket(VehicleType vehicleType, ParkingSpot spot) {
        this.ticketNumber = "T" + counter.getAndIncrement();
        this.vehicleType = vehicleType;
        this.spot = spot;
    }

    public void setSimulatedMinutes(long minutes) {
        this.simulatedMinutes = minutes;
    }

    public long getMinutesParked() {
        return simulatedMinutes;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}

// =========================================================================
// CENTRAL PARKING SYSTEM ARCHITECTURE
// =========================================================================
class ParkingSystem {
    private final ParkingLot lot;
    private final ParkingEventBus eventBus;
    private final PaymentContext paymentContext;
    private final ElevatorStackerControl elevatorControl;
    private final Map<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    public ParkingSystem(ParkingLot lot, ParkingEventBus eventBus, PaymentContext paymentContext, ElevatorStackerControl elevatorControl) {
        this.lot = lot;
        this.eventBus = eventBus;
        this.paymentContext = paymentContext;
        this.elevatorControl = elevatorControl;
    }

    public String enterParking(VehicleType type) {
        ParkingSpot spot = lot.findAndReserveSpot(type);
        if (spot == null) {
            System.out.println("[CORE ENGINE] Intake Denied. Infrastructure Max Capacity Reached.");
            return null;
        }
        ParkingTicket ticket = new ParkingTicket(type, spot);
        activeTickets.put(ticket.getTicketNumber(), ticket);
        eventBus.publish(new EventMessage(SystemEventType.ENTRY_RECORDED, ticket.getTicketNumber()));
        System.out.printf("[CORE ENGINE] Allocated Spot: %s -> Assigned Ticket: %s\n", spot.getSpotNumber(), ticket.getTicketNumber());
        // Asynchronously dispatch mechanical stacking robot
        CompletableFuture.runAsync(() -> elevatorControl.executeMechanicalStacking(ticket.getTicketNumber(), spot.getFloorId()));
        return ticket.getTicketNumber();
    }

    public void exitParking(String ticketNumber, long minutes, CryptoCurrency coin, String wallet) {
        ParkingTicket ticket = activeTickets.remove(ticketNumber);
        if (ticket == null) return;
        ticket.setSimulatedMinutes(minutes);
        double feeUsd = (Math.ceil((double) minutes / 60.0)) * (ticket.getVehicleType() == VehicleType.CAR ? 20 : 10);
        lot.releaseSpot(ticket.getSpot());
        eventBus.publish(new EventMessage(SystemEventType.SPOT_RELEASED, ticket.getSpot().getSpotNumber()));
        System.out.printf("[CORE ENGINE] Completed Stay for %s. Settle USD Amount: $%.2f\n", ticketNumber, feeUsd);
        paymentContext.executeCryptoSettlement(coin, feeUsd, wallet);
    }

    public synchronized void displayLotStatus() {
        lot.displayStatus();
    }
}

// =========================================================================
// RUNTIME ENVIRONMENT
// =========================================================================
public class ParkingLotLLD {
    public static void main(String[] args) throws InterruptedException {
        Map<SpotType, Integer> config = new HashMap<>();
        config.put(SpotType.COMPACT, 1);
        config.put(SpotType.REGULAR, 1);
        ParkingLot lot = new ParkingLot(2, config);
        ParkingEventBus eventBus = new ParkingEventBus();
        PaymentContext paymentContext = new PaymentContext();
        ElevatorStackerControl elevatorControl = new ElevatorStackerControl(1);
        // 1 single mechanical elevator shaft
        ParkingSystem system = new ParkingSystem(lot, eventBus, paymentContext, elevatorControl);
        SurgeVolatilityPublisher surgePublisher = new SurgeVolatilityPublisher(eventBus, lot::getOccupancyRate);
        // Register custom event consumers onto the messaging backbone
        eventBus.registerSubscriber(event -> {
            if (event.getType() == SystemEventType.SURGE_MULTIPLIER_CHANGED) {
                System.out.println("[EVENT BUS CONSUMER] Price Dynamic Matrix Update -> " + event.getContextData());
            }
        });
        surgePublisher.startMonitoring();
        System.out.println("--- Executing Stacking Intake Pipelines ---");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(() -> system.enterParking(VehicleType.CAR));
        executor.execute(() -> system.enterParking(VehicleType.CAR));
        // This second request will trigger the mechanical lock wait condition
        Thread.sleep(1200);
        // Give background worker tasks adequate execution windows
        System.out.println("\n--- Initial Operational Status Report ---");
        system.displayLotStatus();
        surgePublisher.stopMonitoring();
        eventBus.shutdown();
        executor.shutdown();
    }
}