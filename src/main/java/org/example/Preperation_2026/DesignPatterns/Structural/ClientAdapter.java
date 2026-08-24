package org.example.Preperation_2026.DesignPatterns.Structural;

// Existing interface expected by your system
interface LegacyPayment {
    void makePayment(double amount);
}

// New, incompatible API you want to integrate
class ModernPaymentProvider {
    public void doTransaction(double amt) {
        System.out.println("ModernPaymentProvider: Paid " + amt);
    }
}
// Adapter bridging Legacy and Modern APIs
class PaymentAdapter implements LegacyPayment {
    private ModernPaymentProvider modernProvider;
    public PaymentAdapter(ModernPaymentProvider modernProvider) {
        this.modernProvider = modernProvider;
    }
    public void makePayment(double amount) {
        modernProvider.doTransaction(amount);
    }
}

public class ClientAdapter {
    public static void main(String[] args) {
        ModernPaymentProvider newProvider = new ModernPaymentProvider();
        LegacyPayment adapted = new PaymentAdapter(newProvider);
        adapted.makePayment(500.0); // Client code stays unchanged!
    }
}
