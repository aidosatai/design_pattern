package lt.esdc.shapes.warehouse;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.service.RectangleServiceInterface;
import lt.esdc.shapes.service.impl.RectangleServiceImpl;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Tests to verify the correct implementation of the Singleton pattern for ShapeWarehouse.
 */
public class SingletonPatternTest {

    @Test
    public void testSingletonInstance() {
        // Get the instance twice
        ShapeWarehouse instance1 = ShapeWarehouse.getInstance();
        ShapeWarehouse instance2 = ShapeWarehouse.getInstance();
        
        // Both references should point to the same instance
        assertSame(instance1, instance2, "Singleton should return the same instance");
    }
    
    @Test
    public void testSingletonConfiguration() {
        // Get the singleton instance
        ShapeWarehouse warehouse = ShapeWarehouse.getInstance();
        
        // Configure the singleton
        RectangleServiceInterface service = new RectangleServiceImpl();
        warehouse.setRectangleService(service);
        
        // Get another reference and verify configuration is preserved
        ShapeWarehouse anotherReference = ShapeWarehouse.getInstance();
        
        // Create a rectangle and notify the warehouse through observer pattern
        Rectangle rectangle = new Rectangle(
            "test-rect-id",
            new Point(0, 0),
            new Point(0, 4),
            new Point(3, 4),
            new Point(3, 0)
        );
        
        // Add warehouse as observer
        rectangle.addObserver(warehouse);
        
        // Modify rectangle to trigger notification
        rectangle.setPoint1(new Point(1, 1));
        
        // Both references should have been updated
        assertNotNull(warehouse.getPerimeter("test-rect-id"), 
                "Singleton warehouse should have cached the perimeter");
        assertNotNull(warehouse.getArea("test-rect-id"), 
                "Singleton warehouse should have cached the area");
        
        // The other reference should have the same data
        assertEquals(warehouse.getPerimeter("test-rect-id"), 
                anotherReference.getPerimeter("test-rect-id"),
                "Both singleton references should share the same data");
    }
}
