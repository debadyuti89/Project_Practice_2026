package org.example.Preperation_2026.DesignPatterns.Structural;

// Complex system: Movie, Lights, and Sound
class MoviePlayer {
    public void turnOn() { System.out.println("Movie player ON"); }
    public void play(String movie) { System.out.println("Playing: " + movie); }
    public void turnOff() { System.out.println("Movie player OFF"); }
}

class Lights {
    public void dim() { System.out.println("Lights dimmed"); }
    public void brighten() { System.out.println("Lights brightened"); }
}
class SoundSystem {
    public void setSurround() { System.out.println("Surround sound ON"); }
    public void setStereo() { System.out.println("Stereo sound ON"); }
}
// Facade class that hides subsystem complexity
class HomeTheaterFacade {
    private MoviePlayer player;
    private Lights lights;
    private SoundSystem sound;
    public HomeTheaterFacade(MoviePlayer player, Lights lights, SoundSystem sound) {
        this.player = player;
        this.lights = lights;
        this.sound = sound;
    }
    public void watchMovie(String movie) {
        lights.dim();
        sound.setSurround();
        player.turnOn();
        player.play(movie);
    }
    public void endMovie() {
        player.turnOff();
        lights.brighten();
        sound.setStereo();
    }
}

public class ClientFacade {
    public static void main(String[] args) {
        HomeTheaterFacade theater = new HomeTheaterFacade(
                new MoviePlayer(), new Lights(), new SoundSystem()
        );
        theater.watchMovie("Inception");
        theater.endMovie();
    }
}
