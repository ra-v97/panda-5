package application;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    @Test public void testMainIsRunning() {
       Main classUnderTest = new Main();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
