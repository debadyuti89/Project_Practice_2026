package org.example.Preperation_2026.DesignPatterns.LLD_Practice;

import java.util.*;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// ENUMS
enum SeatType {REGULAR, VIP}

enum SeatStatus {AVAILABLE, HELD, BOOKED}

enum BookingStatus {CONFIRMED, CANCELLED}

enum PaymentStatus {PENDING, SUCCESS, FAILED}

// USER HIERARCHY
class User {
    protected String userId;
    protected String name;
    protected String email;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
}

class Admin extends User {
    public Admin(String userId, String name, String email) {
        super(userId, name, email);
    }

    public void addMovie(List<Movie> catalog, Movie movie) {
        catalog.add(movie);
    }

    public void addShow(Screen screen, Show show) {
        screen.addShow(show);
    }
}

class Movie {
    private String movieId;
    private String title;
    private String genre;
    private int durationMins;

    public Movie(String movieId, String title, String genre, int durationMins) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.durationMins = durationMins;
    }

    public String getTitle() {
        return title;
    }
}

class CinemaHall {
    private String hallId;
    private String name;
    private List<Screen> screens = new ArrayList<>();

    public CinemaHall(String hallId, String name) {
        this.hallId = hallId;
        this.name = name;
    }

    public void addScreen(Screen screen) {
        this.screens.add(screen);
    }
}

class Screen {
    private String screenId;
    private String name;
    private int totalSeats;
    private List<Seat> seats = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();

    public Screen(String screenId, String name, int totalSeats) {
        this.screenId = screenId;
        this.name = name;
        this.totalSeats = totalSeats;
        for (int i = 1; i <= totalSeats; i++) {
            SeatType st = (i <= totalSeats / 5) ? SeatType.VIP : SeatType.REGULAR;
            seats.add(new Seat(screenId + "-" + i, st));
        }
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }

    public List<Seat> getSeats() {
        return seats;
    }
}

class Show {
    private String showId;
    private Movie movie;
    private Screen screen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Seat> seats = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Show(String showId, Movie movie, Screen screen, LocalDateTime startTime, LocalDateTime endTime) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = endTime;
        for (Seat seat : screen.getSeats()) {
            this.seats.add(new Seat(seat.getSeatNumber(), seat.getType()));
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public String getShowId() {
        return showId;
    }

    public String getMovieTitle() {
        return movie.getTitle();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void printAvailableSeats() {
        System.out.println("Available seats for show " + showId + " (" + movie.getTitle() + "):");
        int count = 0;
        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                System.out.print(seat.getSeatNumber() + " ");
                count++;
            }
        }
        System.out.println("\nTotal Available: " + count + "\n");
    }
}

// SEAT IMPLEMENTING OPTIMISTIC LOCKING VERSION ENTITY
class Seat {
    private final String seatNumber;
    private final SeatType type;

    // Volatile status to ensure cross-cpu memory visibility updates
    private volatile SeatStatus status;
    // AtomicInteger acts as our DB Version tracker for thread-safe compare-and-swap (CAS) transitions
    private final AtomicInteger version;

    public Seat(String seatNumber, SeatType type) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.status = SeatStatus.AVAILABLE;
        this.version = new AtomicInteger(0);
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatType getType() {
        return type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public int getVersion() {
        return version.get();
    }

    /**
     * Tries to update seat status using an optimistic locking pattern.
     * Compares the expected version at read time with current memory before updating.
     */
    public boolean updateStatusOptimistically(SeatStatus expectedStatus, SeatStatus targetStatus, int expectedVersion) {
        if (this.status == expectedStatus) {
            // Atomic check: If version matches expected version, increment version and change state
            if (version.compareAndSet(expectedVersion, expectedVersion + 1)) {
                this.status = targetStatus;
                return true;
            }
        }
        return false;
    }
}

class Booking {
    private String bookingId;
    private User user;
    private Show show;
    private List<Seat> seats;
    private BookingStatus status;
    private Payment payment;

    public Booking(String bookingId, User user, Show show, List<Seat> seats, BookingStatus status, Payment payment) {
        this.bookingId = bookingId;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = status;
        this.payment = payment;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        for (Seat seat : seats) {
            // Spin-lock rollback style recovery if cancel version conflicts (Rare in cancellations)
            int currentVersion;
            do {
                currentVersion = seat.getVersion();
            } while (!seat.updateStatusOptimistically(SeatStatus.BOOKED, SeatStatus.AVAILABLE, currentVersion));
        }
        System.out.println("[CANCEL] Booking " + bookingId + " cancelled; seats released.");
    }
}

// STRATEGY PAYMENT ENGINE
interface IPaymentService {
    boolean processPayment(double amount);
}

class UPIPaymentService implements IPaymentService {
    public boolean processPayment(double amount) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException ignored) {
        }
        return true;
    }
}

// PRICING ENGINE FACTORY
class PricingFactory {
    public static double calculatePrice(List<Seat> seats, Show show) {
        double basePrice = 0;
        for (Seat seat : seats) {
            basePrice += (seat.getType() == SeatType.VIP) ? 350.00 : 200.00;
        }
        if (show.getShowId().contains("WEEKEND")) {
            basePrice += (seats.size() * 50.00);
        }
        return basePrice;
    }
}

// NON-BLOCKING DECOUPLED SERVICE (No synchronized keyword used)
class BookingService {
    private IPaymentService paymentService;

    public BookingService(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Booking bookTicket(User user, Show show, List<Seat> requestedSeats) {
        // Map data structure to hold snapshot versions read before the actual mutation
        Map<Seat, Integer> seatVersionSnapshot = new HashMap<>();

        // 1. Read Phase (Isolation Check)
        for (Seat seat : requestedSeats) {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                System.out.println("[REJECTED] " + user.getName() + " failed: Seat " + seat.getSeatNumber() + " is already taken.");
                return null;
            }
            // Capture transaction version snapshot state
            seatVersionSnapshot.put(seat, seat.getVersion());
        }

        // 2. Dynamic Pricing Stage
        double totalCost = PricingFactory.calculatePrice(requestedSeats, show);

        // 3. Optimistic Verification & Commit Lock Phase (CAS Simulation)
        List<Seat> successfullyHeldSeats = new ArrayList<>();
        boolean transactionAborted = false;

        for (Seat seat : requestedSeats) {
            int expectedVersion = seatVersionSnapshot.get(seat);

            // Try atomic write commit
            if (seat.updateStatusOptimistically(SeatStatus.AVAILABLE, SeatStatus.HELD, expectedVersion)) {
                successfullyHeldSeats.add(seat);
            } else {
                // Version mismatch detected! Another concurrent thread modified this row first
                transactionAborted = true;
                break;
            }
        }

        // Handle transaction collision failure
        if (transactionAborted) {
            // Rollback any seats we managed to partially hold
            for (Seat seat : successfullyHeldSeats) {
                int currentVer = seat.getVersion();
                seat.updateStatusOptimistically(SeatStatus.HELD, SeatStatus.AVAILABLE, currentVer);
            }
            System.out.println("[OPTIMISTIC LOCK COLLISION] " + user.getName() + " aborted: Seat version mismatch detected.");
            return null;
        }

        // 4. Payment processing (Safe to call now as seats are uniquely reserved under HELD state)
        boolean paymentSuccess = paymentService.processPayment(totalCost);

        if (paymentSuccess) {
            // Finalize seat state to BOOKED
            for (Seat seat : requestedSeats) {
                int currentVer = seat.getVersion();
                seat.updateStatusOptimistically(SeatStatus.HELD, SeatStatus.BOOKED, currentVer);
            }
            Payment payment = new Payment(UUID.randomUUID().toString(), PaymentStatus.SUCCESS, totalCost, LocalDateTime.now());
            Booking booking = new Booking(UUID.randomUUID().toString(), user, show, requestedSeats, BookingStatus.CONFIRMED, payment);
            show.addBooking(booking);
            System.out.println("[SUCCESS] " + user.getName() + " confirmed booking via Optimistic Locking! ID: " + booking.getBookingId());
            return booking;
        } else {
            // Release held seats if payment gateway breaks down
            for (Seat seat : requestedSeats) {
                int currentVer = seat.getVersion();
                seat.updateStatusOptimistically(SeatStatus.HELD, SeatStatus.AVAILABLE, currentVer);
            }
            System.out.println("[FAILED] " + user.getName() + " failed due to payment gateway error.");
            return null;
        }
    }
}

class Payment {
    private String paymentId;
    private PaymentStatus status;
    private double amount;
    private LocalDateTime paymentTime;

    public Payment(String paymentId, PaymentStatus status, double amount, LocalDateTime paymentTime) {
        this.paymentId = paymentId;
        this.status = status;
        this.amount = amount;
        this.paymentTime = paymentTime;
    }
}

// MULTI-THREADED TESTING LAB HARNESS
public class BookMyShowLLD {
    public static void main(String[] args) throws InterruptedException {
        List cat = new ArrayList<>();
        Admin admin = new Admin("admin1", "CinemaAdmin", "admin@cinema.com");
        Movie m1 = new Movie("mov1", "Interstellar", "Sci-Fi", 169);
        admin.addMovie(cat, m1);
        CinemaHall hall = new CinemaHall("hall1", "Grand Multiplex");
        Screen s1 = new Screen("S1", "Screen 1", 10);
        hall.addScreen(s1);
        Show show1 = new Show("show1-WEEKEND", m1, s1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(3));
        admin.addShow(s1, show1);
        BookingService bookingService = new BookingService(new UPIPaymentService());
        System.out.println("====== INITIAL CINEMA AUDIT ======");
        show1.printAvailableSeats();
        // Target exact duplicate rows to force thread racing collisions
        List targetSeats = new ArrayList<>();
        targetSeats.add(show1.getSeats().get(0));
        // Target S1-1
        targetSeats.add(show1.getSeats().get(1)); // Target S1-2
        int threadPoolCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolCount);
        System.out.println("====== SPINNING UP NON-BLOCKING CONCURRENT USERS CONFLICTING FOR SEATS S1-1 & S1-2 ======");
        for (int i = 1; i <= threadPoolCount; i++) {
            final String name = "UserThread-" + i;
            executor.submit(() -> {
                User virtualUser = new User("uid-" + name, name, name + "@test.com");
                bookingService.bookTicket(virtualUser, show1, targetSeats);
            });
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("\n====== POST CONCURRENCY RUN AUDIT ======");
        show1.printAvailableSeats();
    }
}