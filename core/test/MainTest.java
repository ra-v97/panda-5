import org.junit.Test;
import pl.edu.agh.panda5.Panda5;

import static org.junit.Assert.*;

public class MainTest {
    @Test public void testMainIsRunning() {
       Panda5 classUnderTest = new Panda5();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
