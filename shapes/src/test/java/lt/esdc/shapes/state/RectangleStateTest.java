package lt.esdc.shapes.state;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RectangleStateTest {
    
    @Test
    public void testRegularRectangleState() {
        RectangleState state = new RegularRectangleState();
        assertEquals("Rectangle", state.getStateName());
    }
    
    @Test
    public void testSquareState() {
        RectangleState state = new SquareState();
        assertEquals("Square", state.getStateName());
    }
    
    @Test
    public void testRhombusState() {
        RectangleState state = new RhombusState();
        assertEquals("Rhombus", state.getStateName());
    }
    
    @Test
    public void testTrapezoidState() {
        RectangleState state = new TrapezoidState();
        assertEquals("Trapezoid", state.getStateName());
    }
}
