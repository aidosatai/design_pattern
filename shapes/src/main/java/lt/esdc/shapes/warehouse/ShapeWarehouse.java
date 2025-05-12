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
    
    public Double getPerimeter(String shapeId) {
        return perimeterMap.get(shapeId);
    }
    
    public Double getArea(String shapeId) {
        return areaMap.get(shapeId);
    }
    
    public void clear() {
        perimeterMap.clear();
        areaMap.clear();
    }
    
    @Override
    public void onShapeChanged(String shapeId, Shape shape) {
        if (rectangleService != null && shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            double perimeter = rectangle.calculatePerimeter();
            double area = rectangle.calculateArea();
            
            storePerimeter(shapeId, perimeter);
            storeArea(shapeId, area);
        }
    }
}
