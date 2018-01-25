package drivers;

import domini.Ball;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

class BallDriverJUnit {
    @Test
    void testGetColor() {
        Ball b = new Ball();
        int result = b.getColor();
        assertEquals( 0, result, 1 );
    }

    @Test
    void testSetColor() {
        Ball b = new Ball();
        b.setColor(1);
        assertEquals( 1, b.getColor(), 1 );
    }
}