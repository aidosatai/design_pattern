package lt.esdc.shapes.warehouse;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.observer.ShapeObserver;
import lt.esdc.shapes.service.RectangleServiceInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Warehouse class that stores and manages shape properties.
 * 
 * <p>This class implements the Singleton pattern to ensure that only one instance
 * exists throughout the application. It also implements the Observer pattern
 * by implementing ShapeObserver to automatically update cached calculations
 * when shapes are modified.</p>
 * 
 * <p>The warehouse maintains cached values for shape properties such as perimeter
 * and area to avoid redundant calculations.</p>
 */
public class ShapeWarehouse implements ShapeObserver {
    /**
     * Singleton instance of the warehouse
     * Using non-thread-safe implementation as per requirements
     */
    private static ShapeWarehouse instance;
    
    /**
     * Cache maps for storing calculated properties
     */
    private final Map<String, Double> perimeterMap;
    private final Map<String, Double> areaMap;
    
    /**
     * Rectangle service for geometry calculations
     * Using interface reference for better abstraction
     */
    private RectangleServiceInterface rectangleService;
    
    /**
     * Private constructor to prevent direct instantiation (Singleton pattern).
     * Initializes the internal cache maps.
     */
    private ShapeWarehouse() {
        perimeterMap = new HashMap<>();
        areaMap = new HashMap<>();
    }
    
    /**
     * Gets the singleton instance of the warehouse.
     * Uses lazy initialization (instance created on first call).
     * 
     * @return the singleton instance of ShapeWarehouse
     */
    public static ShapeWarehouse getInstance() {
        if (instance == null) {
            instance = new ShapeWarehouse();
        }
        return instance;
    }
    
    /**
     * Sets the rectangle service instance used for calculations.
     * Uses the interface type for better abstraction and testability.
     * 
     * @param rectangleService the rectangle service implementation to use
     */
    public void setRectangleService(RectangleServiceInterface rectangleService) {
        this.rectangleService = rectangleService;
    }
    
    public void storePerimeter(String shapeId, double perimeter) {
        perimeterMap.put(shapeId, perimeter);
    }
    
    public void storeArea(String shapeId, double area) {
        areaMap.put(shapeId, area);
    }
    
    public double getPerimeter(String shapeId) {
        return perimeterMap.getOrDefault(shapeId, 0.0);
    }

    public double getArea(String shapeId) {
        return areaMap.getOrDefault(shapeId, 0.0);
    }
    
    public void clear() {
        perimeterMap.clear();
        areaMap.clear();
    }
    
    @Override
    public void update(Shape shape) {
        if (rectangleService != null && shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            double perimeter = rectangleService.calculatePerimeter(rectangle);
            double area = rectangleService.calculateArea(rectangle);

            storePerimeter(rectangle.getId(), perimeter);
            storeArea(rectangle.getId(), area);
        }
    }
}
