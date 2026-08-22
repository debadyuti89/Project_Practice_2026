package org.example.Preperation_2026.parking_lot.model;

public class PaymentReceipt {
    private final String receiptId;
    private final int amount;
    private final PaymentStatus paymentStatus;

    public PaymentReceipt(String receiptId, int amount, PaymentStatus paymentStatus) {
        this.receiptId = receiptId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public int getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}