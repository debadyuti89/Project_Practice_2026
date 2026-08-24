package org.example.Preperation_2026.DesignPatterns.Behavioral;

// PaymentStrategy.java
interface PaymentStrategy {
    void pay(double amount);
}

// Card payment strategy
class CardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid by card: " + amount);
    }
}

// UPI payment strategy
class UPIPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid by UPI: " + amount);
    }
}

// Context
class CheckoutContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}

public class ClientStrategy {
    public static void main(String[] args) {
        CheckoutContext checkout = new CheckoutContext();
        checkout.setPaymentStrategy(new CardPayment());
        checkout.checkout(150.0);

        checkout.setPaymentStrategy(new UPIPayment());
        checkout.checkout(200.0);
    }
}
