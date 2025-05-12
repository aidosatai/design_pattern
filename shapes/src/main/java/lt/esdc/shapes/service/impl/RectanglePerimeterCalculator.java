package lt.esdc.shapes.service.impl;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.service.PerimeterCalculator;

public class RectanglePerimeterCalculator implements PerimeterCalculator<Rectangle> {
    
    @Override
    public double calculatePerimeter(Rectangle rectangle) throws InvalidShapeException {
        if (rectangle == null) {
            throw new InvalidShapeException("Rectangle cannot be null");
        }
        
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Вычисление периметра как суммы длин всех сторон
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        
        return side1 + side2 + side3 + side4;
    }
    
    private double calculateDistance(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
