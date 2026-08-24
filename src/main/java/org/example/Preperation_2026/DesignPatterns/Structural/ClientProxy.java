package org.example.Preperation_2026.DesignPatterns.Structural;

// Interface
interface Image {
    void display();
}

// Heavy real object
class RealImage implements Image {
    private String filename;
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    private void loadFromDisk() {
        System.out.println("Loading " + filename + " from disk...");
    }
    public void display() {
        System.out.println("Displaying " + filename);
    }
}
// Proxy object
class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    public ImageProxy(String filename) {
        this.filename = filename;
    }
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // Lazy load
        }
        realImage.display();
    }
}

public class ClientProxy {
    public static void main(String[] args) {
        Image img = new ImageProxy("cat_photo.jpg");
        System.out.println("Image object created.");
        img.display(); // Loads + displays
        img.display(); // Only displays, no reload
    }
}
