package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.validator.RectangleValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RectangleFactoryTest {
    
    private RectangleFactory factory;
    
    @BeforeMethod
    public void setUp() {
        factory = new RectangleFactory(new RectangleValidator());
    }
    
    @Test
    public void testCreateValidRectangle() throws InvalidShapeException {
        // Point data for a valid rectangle
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 2);
        Point p3 = new Point(3, 2);
        Point p4 = new Point(3, 0);
        
        Rectangle rectangle = factory.createShape(null, p1, p2, p3, p4);
        
        assertNotNull(rectangle);
        assertNotNull(rectangle.getId()); // ID should be generated automatically
        assertEquals(p1, rectangle.getPoint1());
        assertEquals(p2, rectangle.getPoint2());
        assertEquals(p3, rectangle.getPoint3());
        assertEquals(p4, rectangle.getPoint4());
    }
    
    @Test(expectedExceptions = InvalidShapeException.class)
    public void testCreateInvalidRectangle() throws InvalidShapeException {
        // Points that don't form a valid rectangle (three on the same line)
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(3, 0);
        
        factory.createShape(null, p1, p2, p3, p4);
    }
    
    @Test
    public void testCreateWithProvidedId() throws InvalidShapeException {
        String customId = "custom-rectangle-id";
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 2);
        Point p3 = new Point(3, 2);
        Point p4 = new Point(3, 0);
        
        Rectangle rectangle = factory.createShape(customId, p1, p2, p3, p4);
        
        assertEquals(customId, rectangle.getId());
    }
}
