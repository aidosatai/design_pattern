package lt.esdc.shapes.warehouse;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.observer.ShapeObserver;
import lt.esdc.shapes.service.RectangleService;

import java.util.HashMap;
import java.util.Map;

public class ShapeWarehouse implements ShapeObserver {
    private static ShapeWarehouse instance;
    
    private final Map<String, Double> perimeterMap;
    private final Map<String, Double> areaMap;
    private RectangleService rectangleService;
    
    private ShapeWarehouse() {
        perimeterMap = new HashMap<>();
        areaMap = new HashMap<>();
    }
    
    public static ShapeWarehouse getInstance() {
        if (instance == null) {
            instance = new ShapeWarehouse();
        }
        return instance;
    }
    
    public void setRectangleService(RectangleService rectangleService) {
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
