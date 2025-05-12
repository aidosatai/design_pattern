package lt.esdc.shapes.observer;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ShapeObserverTest {
    
    private TestShapeSubject subject;
    private TestShapeObserver observer;
    
    @BeforeMethod
    public void setUp() {
        subject = new TestShapeSubject();
        observer = new TestShapeObserver();
        subject.addObserver(observer);
    }
    
    @Test
    public void testAddObserver() {
        TestShapeObserver newObserver = new TestShapeObserver();
        subject.addObserver(newObserver);
        
        subject.notifyObservers(subject);
        
        assertTrue(observer.isNotified());
        assertTrue(newObserver.isNotified());
    }
    
    @Test
    public void testRemoveObserver() {
        subject.removeObserver(observer);
        
        subject.notifyObservers(subject);
        
        assertTrue(!observer.isNotified());
    }
    
    @Test
    public void testMultipleObservers() {
        TestShapeObserver observer2 = new TestShapeObserver();
        TestShapeObserver observer3 = new TestShapeObserver();
        
        subject.addObserver(observer2);
        subject.addObserver(observer3);
        
        subject.notifyObservers(subject);
        
        assertTrue(observer.isNotified());
        assertTrue(observer2.isNotified());
        assertTrue(observer3.isNotified());
    }
    
    @Test
    public void testIntegrationWithRectangle() {
        Rectangle rectangle = new Rectangle(
                "test-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(3, 2),
                new Point(3, 0)
        );
        
        TestShapeObserver rectangleObserver = new TestShapeObserver();
        rectangle.addObserver(rectangleObserver);
        
        rectangle.setPoint1(new Point(1, 1));
        
        assertTrue(rectangleObserver.isNotified());
        assertEquals(rectangle, rectangleObserver.getLastUpdatedShape());
    }
    
    // Helper test classes
    private static class TestShapeSubject extends ShapeSubject {}
    
    private static class TestShapeObserver implements ShapeObserver {
        private boolean notified = false;
        private Shape lastUpdatedShape;
        
        @Override
        public void update(Shape shape) {
            notified = true;
            lastUpdatedShape = shape;
        }
        
        public boolean isNotified() {
            return notified;
        }
        
        public Shape getLastUpdatedShape() {
            return lastUpdatedShape;
        }
    }
}
