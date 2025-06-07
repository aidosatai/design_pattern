package lt.esdc.shapes.entity;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Tests for the {@link Point} class, including change notification functionality.
 */
public class PointTest {

    @Test
    public void testPointCreation() {
        Point point = new Point(10.0, 20.0);
        
        assertEquals(10.0, point.getX(), 0.001, "X coordinate should be set correctly");
        assertEquals(20.0, point.getY(), 0.001, "Y coordinate should be set correctly");
    }
    
    @Test
    public void testSetters() {
        Point point = new Point(1.0, 2.0);
        
        point.setX(5.0);
        point.setY(6.0);
        
        assertEquals(5.0, point.getX(), 0.001, "X coordinate should be updated");
        assertEquals(6.0, point.getY(), 0.001, "Y coordinate should be updated");
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Point point1 = new Point(1.0, 2.0);
        Point point2 = new Point(1.0, 2.0);
        Point point3 = new Point(3.0, 4.0);
        
        assertTrue(point1.equals(point2), "Equal points should be equal");
        assertFalse(point1.equals(point3), "Different points should not be equal");
        assertEquals(point1.hashCode(), point2.hashCode(), "Equal points should have same hash code");
    }
    
    @Test
    public void testChangeListenerNotification() {
        Point point = new Point(1.0, 2.0);
        TestChangeListener listener = new TestChangeListener();
        point.setChangeListener(listener);
        
        // Setting same value should not notify
        point.setX(1.0);
        assertFalse(listener.wasNotified(), "Listener should not be notified when value doesn't change");
        listener.reset();
        
        // Setting new value should notify
        point.setX(3.0);
        assertTrue(listener.wasNotified(), "Listener should be notified when X changes");
        assertEquals(point, listener.getChangedPoint(), "Changed point should be passed to listener");
        listener.reset();
        
        point.setY(4.0);
        assertTrue(listener.wasNotified(), "Listener should be notified when Y changes");
        assertEquals(point, listener.getChangedPoint(), "Changed point should be passed to listener");
    }
    
    /**
     * Test implementation of ChangeListener for testing notification.
     */
    private static class TestChangeListener implements Point.ChangeListener {
        private boolean notified = false;
        private Point changedPoint = null;
        
        @Override
        public void onPointChanged(Point point) {
            notified = true;
            changedPoint = point;
        }
        
        public boolean wasNotified() {
            return notified;
        }
        
        public Point getChangedPoint() {
            return changedPoint;
        }
        
        public void reset() {
            notified = false;
            changedPoint = null;
        }
    }
}
