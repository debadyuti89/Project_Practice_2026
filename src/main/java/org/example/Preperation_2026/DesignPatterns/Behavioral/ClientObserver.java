package org.example.Preperation_2026.DesignPatterns.Behavioral;

import java.util.*;

interface Observer {
    void update(String message);
}

class Subscriber implements Observer {
    private String name;
    public Subscriber(String name) {
        this.name = name;
    }
    public void update(String message) {
        System.out.println(name + " received update: " + message);
    }
}

class Channel {
    private List<Observer> subscribers = new ArrayList<>();
    public void subscribe(Observer observer) { subscribers.add(observer); }
    public void unsubscribe(Observer observer) { subscribers.remove(observer); }
    public void notifyObservers(String message) {
        for (Observer obs : subscribers) {
            obs.update(message);
        }
    }
    // E.g., when a new video is uploaded
    public void uploadVideo(String videoTitle) {
        System.out.println("New video uploaded: " + videoTitle);
        notifyObservers("New video: " + videoTitle);
    }
}

public class ClientObserver {
    public static void main(String[] args) {
        Channel channel = new Channel();
        Observer alice = new Subscriber("Alice");
        Observer bob = new Subscriber("Bob");

        channel.subscribe(alice);
        channel.subscribe(bob);

        channel.uploadVideo("Design Patterns in Java");
        channel.unsubscribe(alice);
        channel.uploadVideo("Observer Pattern Explained");
    }
}
