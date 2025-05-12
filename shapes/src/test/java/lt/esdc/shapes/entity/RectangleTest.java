package lt.esdc.shapes.entity;

import lt.esdc.shapes.observer.ShapeObserver;
import lt.esdc.shapes.state.RectangleState;
import lt.esdc.shapes.state.SquareState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RectangleTest {

    private Rectangle rectangle;
    private TestShapeObserver observer;

    @BeforeMethod
    public void setUp() {
        rectangle = new Rectangle(
                "test-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(3, 2),
                new Point(3, 0)
        );
        observer = new TestShapeObserver();
        rectangle.addObserver(observer);
    }

    @Test
    public void testNotificationOnPointChange() {
        Point newPoint = new Point(1, 1);
        
        rectangle.setPoint1(newPoint);
        
        assertTrue(observer.isNotified());
        assertEquals(rectangle, observer.getLastUpdatedShape());
    }
    
    @Test
    public void testStateChange() {
        RectangleState newState = new SquareState();
        
        rectangle.setState(newState);
        
        assertEquals("Square", rectangle.getState().getStateName());
    }
    
    @Test
    public void testEqualityAndHash() {
        Rectangle sameRectangle = new Rectangle(
                "test-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(3, 2),
                new Point(3, 0)
        );
        
        Rectangle differentRectangle = new Rectangle(
                "different-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(3, 2),
                new Point(3, 0)
        );
        
        assertTrue(rectangle.equals(sameRectangle));
        assertTrue(rectangle.hashCode() == sameRectangle.hashCode());
        assertTrue(!rectangle.equals(differentRectangle));
    }
    
    // Inner class for testing observer pattern
    private static class TestShapeObserver implements ShapeObserver {
        private boolean notified = false;
        private Shape lastUpdatedShape;
        
        @Override
        public void update(Shape shape) {
            this.notified = true;
            this.lastUpdatedShape = shape;
        }
        
        public boolean isNotified() {
            return notified;
        }
        
        public Shape getLastUpdatedShape() {
            return lastUpdatedShape;
        }
    }
}
