package org.example.Preperation_2026.DesignPatterns.Creational;

import java.util.*;

class Order {
    private String orderId;
    private String customer;
    private List<String> items;
    private String shippingAddress;
    private boolean giftWrap;
    private String note;

    private Order(OrderBuilder builder) {
        this.orderId = builder.orderId;
        this.customer = builder.customer;
        this.items = builder.items;
        this.shippingAddress = builder.shippingAddress;
        this.giftWrap = builder.giftWrap;
        this.note = builder.note;
    }

    @Override
    public String toString() {
        return "Order[" + orderId + "] for " + customer + "\nItems: " + items +
                "\nShip to: " + shippingAddress + (giftWrap ? " [Gift Wrap]" : "") +
                (note != null ? "\nNote: " + note : "");
    }

    // Static Builder
    public static class OrderBuilder {
        private String orderId;
        private String customer;
        private List<String> items = new ArrayList<>();
        private String shippingAddress;
        private boolean giftWrap = false;
        private String note;

        public OrderBuilder(String orderId, String customer) {
            this.orderId = orderId;
            this.customer = customer;
        }

        public OrderBuilder addItem(String item) {
            items.add(item);
            return this;
        }

        public OrderBuilder setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public OrderBuilder setGiftWrap(boolean giftWrap) {
            this.giftWrap = giftWrap;
            return this;
        }

        public OrderBuilder setNote(String note) {
            this.note = note;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
public class ClientBuilder {
    public static void main(String[] args) {
        Order order = new Order.OrderBuilder("ORD123", "Alice")
                .addItem("Pizza").addItem("Coke")
                .setShippingAddress("221B Baker St, London")
                .setGiftWrap(true)
                .setNote("Happy Birthday!")
                .build();
        System.out.println(order);
    }
}
