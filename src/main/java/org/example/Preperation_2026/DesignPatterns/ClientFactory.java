package org.example.Preperation_2026.DesignPatterns;

interface PaymentProcessor {
    void process(double amount);
}

class CardPaymentProcessor implements PaymentProcessor {
    public void process(double amount) {
        System.out.println("Processing Card payment: " + amount);
    }
}

class UPIPaymentProcessor implements PaymentProcessor {
    public void process(double amount) {
        System.out.println("Processing UPI payment: " + amount);
    }
}

enum PaymentType {
CARD, UPI
}

class PaymentProcessorFactory {
    public static PaymentProcessor getPaymentProcessor(PaymentType type) {
        return switch (type) {
            case CARD -> new CardPaymentProcessor();
            case UPI -> new UPIPaymentProcessor();
            default -> throw new IllegalArgumentException("Unknown payment type: " + type);
        };
    }
}

public class ClientFactory {
    public static void main(String[] args) {
        PaymentProcessor cardProcessor = PaymentProcessorFactory.getPaymentProcessor(PaymentType.CARD);
        PaymentProcessor upiProcessor = PaymentProcessorFactory.getPaymentProcessor(PaymentType.UPI);

        cardProcessor.process(250.0);
        upiProcessor.process(100.0);
    }
}
