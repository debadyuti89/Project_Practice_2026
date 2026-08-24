package org.example.Preperation_2026.DesignPatterns.Behavioral;

// Base interface
interface Coffee {
    String getDescription();
    double getCost();
}

// Concrete base class
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Plain Coffee"; }
    public double getCost() { return 5.0; }
}

// Decorator base class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    public CoffeeDecorator(Coffee c) { this.coffee = c; }
    public String getDescription() { return coffee.getDescription(); }
    public double getCost() { return coffee.getCost(); }
}

// Concrete decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee c) { super(c); }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double getCost() { return coffee.getCost() + 1.5; }
}

class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee c) { super(c); }
    public String getDescription() { return coffee.getDescription() + ", Whip"; }
    public double getCost() { return coffee.getCost() + 2.0; }
}

public class ClientDecorator {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new WhipDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());
    }
}
