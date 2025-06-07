package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.exception.InvalidShapeException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for the Factory Method pattern implementation in ShapeFactory and RectangleFactory.
 */
public class FactoryMethodPatternTest {

    private RectangleFactory rectangleFactory;
    private Point point1, point2, point3, point4;

    @BeforeMethod
    public void setUp() {
        rectangleFactory = new RectangleFactory();
        point1 = new Point(0, 0);
        point2 = new Point(0, 4);
        point3 = new Point(3, 4);
        point4 = new Point(3, 0);
    }

    @Test
    public void testCreateShapeWithPoints() {
        Rectangle rectangle = (Rectangle) rectangleFactory.createShape(new Point[]{point1, point2, point3, point4});
        
        assertNotNull(rectangle, "Factory should create a non-null rectangle");
        assertNotNull(rectangle.getId(), "Created rectangle should have an ID");
        assertEquals(rectangle.getPoint1(), point1, "Point1 should match");
        assertEquals(rectangle.getPoint2(), point2, "Point2 should match");
        assertEquals(rectangle.getPoint3(), point3, "Point3 should match");
        assertEquals(rectangle.getPoint4(), point4, "Point4 should match");
    }
    
    @Test
    public void testCreateShapeWithVarargs() {
        Rectangle rectangle = (Rectangle) rectangleFactory.createShape(point1, point2, point3, point4);
        
        assertNotNull(rectangle, "Factory should create a non-null rectangle with varargs");
        assertNotNull(rectangle.getId(), "Created rectangle should have an ID");
    }
    
    @Test
    public void testCreateShapeWithCustomId() {
        String customId = "custom-rectangle-id";
        Rectangle rectangle = (Rectangle) rectangleFactory.createShape(customId, 
                new Point[]{point1, point2, point3, point4});
        
        assertNotNull(rectangle, "Factory should create a non-null rectangle");
        assertEquals(customId, rectangle.getId(), "Created rectangle should have the custom ID");
    }
    
    @Test(expectedExceptions = InvalidShapeException.class)
    public void testCreateShapeWithInvalidNumberOfPoints() {
        rectangleFactory.createShape(new Point[]{point1, point2, point3});
    }
    
    @Test(expectedExceptions = InvalidShapeException.class)
    public void testCreateShapeWithNullPoints() {
        rectangleFactory.createShape(new Point[]{point1, null, point3, point4});
    }
    
    @Test
    public void testFactoryValidation() {
        // Create a rectangle with non-perpendicular sides
        Point invalidPoint4 = new Point(4, 1); // This makes a non-rectangular shape
        
        try {
            rectangleFactory.createShape(point1, point2, point3, invalidPoint4);
            fail("Should throw InvalidShapeException for non-rectangular shape");
        } catch (InvalidShapeException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("valid"), 
                    "Exception message should indicate validation failure");
        }
    }
}
