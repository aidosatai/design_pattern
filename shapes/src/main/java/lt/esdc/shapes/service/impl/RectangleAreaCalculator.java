package lt.esdc.shapes.service.impl;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.service.AreaCalculator;

public class RectangleAreaCalculator implements AreaCalculator<Rectangle> {
    
    @Override
    public double calculateArea(Rectangle rectangle) throws InvalidShapeException {
        if (rectangle == null) {
            throw new InvalidShapeException("Rectangle cannot be null");
        }
        
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Вычисление площади по формуле Гаусса для произвольного четырехугольника
        double area = 0.5 * Math.abs(
            (p1.getX() * p2.getY() - p2.getX() * p1.getY()) +
            (p2.getX() * p3.getY() - p3.getX() * p2.getY()) +
            (p3.getX() * p4.getY() - p4.getX() * p3.getY()) +
            (p4.getX() * p1.getY() - p1.getX() * p4.getY())
        );
        
        return area;
    }
}
