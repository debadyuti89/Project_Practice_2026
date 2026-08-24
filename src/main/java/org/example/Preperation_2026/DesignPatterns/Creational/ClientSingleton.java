package org.example.Preperation_2026.DesignPatterns.Creational;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DbConnection {
    String url;
    String password;
    String userName;

    private static volatile DbConnection instance;
    private static final Lock l1 = new ReentrantLock();

    private DbConnection(String url, String password, String userName) {
        this.url = url;
        this.password = password;
        this.userName = userName;
    }

    public static DbConnection getInstance(String url, String pass, String userName) {
        if (instance == null) {
            l1.lock();
            try {
                if (instance == null) {
                    instance = new DbConnection(url, pass, userName);
                }
            } finally {
                l1.unlock();
            }
        }
        return instance;
    }

    // Helper method to print connection details
    public void printDetails() {
        System.out.println("URL: " + url + " | Password: " + password + " | User: " + userName);
    }
}
public class ClientSingleton {
    public static void main(String[] args) {
        // First initialization
        DbConnection db1 = DbConnection.getInstance("localhost", "1213", "himmitt");
        System.out.print("db1 details: ");
        db1.printDetails();

        // Second call with different parameters
        DbConnection db2 = DbConnection.getInstance("localhost", "9887", "himmitt");
        System.out.print("db2 details: ");
        db2.printDetails();

        // Verification check
        System.out.println("\nAre both variables pointing to the exact same instance?");
        System.out.println("Result: " + (db1 == db2));
    }
}
