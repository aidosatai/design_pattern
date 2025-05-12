package lt.esdc.shapes.warehouse;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.service.RectangleService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class ShapeWarehouseTest {
    
    private ShapeWarehouse warehouse;
    private Rectangle testRectangle;
    private RectangleService mockService;
    
    @BeforeMethod
    public void setUp() {
        warehouse = ShapeWarehouse.getInstance();
        
        testRectangle = new Rectangle(
                "test-id",
                new Point(0, 0),
                new Point(0, 3),
                new Point(4, 3),
                new Point(4, 0)
        );
        
        // Creating a mock service that returns fixed values
        mockService = new RectangleService() {
            @Override
            public double calculateArea(Rectangle rectangle) {
                return 12.0; // Fixed area for test
            }
            
            @Override
            public double calculatePerimeter(Rectangle rectangle) {
                return 14.0; // Fixed perimeter for test
            }
        };
        
        warehouse.setRectangleService(mockService);
    }
    
    @Test
    public void testSingletonPattern() {
        ShapeWarehouse instance1 = ShapeWarehouse.getInstance();
        ShapeWarehouse instance2 = ShapeWarehouse.getInstance();
        
        assertSame(instance1, instance2);
    }
    
    @Test
    public void testStoreAndRetrieveValues() {
        String shapeId = "shape1";
        double perimeter = 10.0;
        double area = 25.0;
        
        warehouse.storePerimeter(shapeId, perimeter);
        warehouse.storeArea(shapeId, area);
        
        assertEquals(perimeter, warehouse.getPerimeter(shapeId), 0.001);
        assertEquals(area, warehouse.getArea(shapeId), 0.001);
    }
    
    @Test
    public void testObserverUpdate() {
        warehouse.update(testRectangle);
        
        // Verify the warehouse stored calculated values
        assertEquals(14.0, warehouse.getPerimeter(testRectangle.getId()), 0.001);
        assertEquals(12.0, warehouse.getArea(testRectangle.getId()), 0.001);
    }
    
    @Test
    public void testNonExistentShape() {
        double perimeter = warehouse.getPerimeter("non-existent-id");
        double area = warehouse.getArea("non-existent-id");
        
        assertEquals(0.0, perimeter, 0.001);
        assertEquals(0.0, area, 0.001);
    }
}
