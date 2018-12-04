package application;

/**
 * Main class for PandaGame app
 * Used for starting the programme
 *
 */
public class Main {

    /**
     * @return Greeting message
     */
    public String getGreeting() {

        return "Hello world.";
    }

    /**
     * Starts the game.
     *
     * @param args app arguments
     *
     */
    public static void main(String[] args) {
        System.out.println(new Main().getGreeting());
    }
}
