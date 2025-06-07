package lt.esdc.shapes.entity;

import lt.esdc.shapes.observer.ShapeObserver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

/**
 * Integration tests for Point and Rectangle classes, focusing on observer notification.
 */
public class PointRectangleIntegrationTest {
    
    private Rectangle rectangle;
    private Point point1, point2, point3, point4;
    private TestShapeObserver observer;
    
    @BeforeMethod
    public void setUp() {
        // Create rectangle with four points
        point1 = new Point(0, 0);
        point2 = new Point(0, 5);
        point3 = new Point(5, 5);
        point4 = new Point(5, 0);
        
        rectangle = new Rectangle("test-rect", point1, point2, point3, point4);
        observer = new TestShapeObserver();
        rectangle.addObserver(observer);
    }
    
    @Test
    public void testPointChangesNotifyRectangleObservers() {
        // Initially the observer is not notified
        assertFalse(observer.isNotified(), "Observer should not be notified initially");
        
        // Modify point1's X coordinate
        point1.setX(1.0);
        
        // Observer should be notified of the change
        assertTrue(observer.isNotified(), "Observer should be notified when point coordinate changes");
        assertEquals(rectangle, observer.getLastUpdatedShape(), "The rectangle should be the updated shape");
        
        // Reset and test Y coordinate change
        observer.reset();
        point2.setY(6.0);
        
        assertTrue(observer.isNotified(), "Observer should be notified when point coordinate changes");
    }
    
    @Test
    public void testMultiplePointChangesNotifyOnce() {
        // Make multiple changes to points
        observer.reset();
        
        // Change multiple coordinates in sequence
        point1.setX(1.0);
        point1.setY(1.0);
        point2.setX(1.0);
        
        // Observer should be notified for each change
        assertEquals(3, observer.getNotificationCount(), "Observer should be notified for each change");
    }
    
    @Test
    public void testRectangleStateUpdatedAfterPointChange() {
        // Initially the rectangle is a square
        assertEquals("Square", rectangle.getState().getStateName());
        
        // Change point to make it not a square
        point1.setX(1.0);
        
        // State should be updated
        assertNotEquals("Square", rectangle.getState().getStateName(), "Rectangle state should be updated after point change");
    }

    /**
     * Test observer implementation that counts notifications.
     */
    private static class TestShapeObserver implements ShapeObserver {
        private boolean notified = false;
        private Shape lastUpdatedShape;
        private int notificationCount = 0;
        
        @Override
        public void update(Shape shape) {
            notified = true;
            lastUpdatedShape = shape;
            notificationCount++;
        }
        
        public boolean isNotified() {
            return notified;
        }
        
        public Shape getLastUpdatedShape() {
            return lastUpdatedShape;
        }
        
        public int getNotificationCount() {
            return notificationCount;
        }
        
        public void reset() {
            notified = false;
            lastUpdatedShape = null;
            notificationCount = 0;
        }
    }
}
