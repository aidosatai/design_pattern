package lt.esdc.shapes.validator;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;

public class RectangleValidator implements Validator<Rectangle> {

    @Override
    public ValidationResult validate(Rectangle rectangle) {
        if (rectangle == null) {
            return ValidationResult.invalid("Rectangle cannot be null");
        }
        
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        if (p1 == null || p2 == null || p3 == null || p4 == null) {
            return ValidationResult.invalid("All points of rectangle must be non-null");
        }
        
        if (areCollinear(p1, p2, p3) || areCollinear(p1, p2, p4) || 
            areCollinear(p1, p3, p4) || areCollinear(p2, p3, p4)) {
            return ValidationResult.invalid("Three points of rectangle cannot be collinear");
        }
        
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        double diagonal1 = calculateDistance(p1, p3);
        double diagonal2 = calculateDistance(p2, p4);
        
        // Check if it's a convex quadrilateral
        if (!isConvexQuadrilateral(p1, p2, p3, p4)) {
            return ValidationResult.invalid("Rectangle must be a convex quadrilateral");
        }
        
        // Check if sides form a rectangle:
        // 1. Opposite sides should be equal
        // 2. Diagonals should be equal
        boolean oppositeSidesEqual = Math.abs(side1 - side3) < 1e-6 && Math.abs(side2 - side4) < 1e-6;
        boolean diagonalsEqual = Math.abs(diagonal1 - diagonal2) < 1e-6;
        
        if (!(oppositeSidesEqual && diagonalsEqual)) {
            return ValidationResult.invalid("Shape must have equal opposite sides and equal diagonals to be a rectangle");
        }
        
        return ValidationResult.valid();
    }
    
    private boolean areCollinear(Point p1, Point p2, Point p3) {
        // Calculate the area of the triangle formed by the three points
        // If the area is zero, the points are collinear
        double area = 0.5 * Math.abs((p1.getX() * (p2.getY() - p3.getY()) + 
                                       p2.getX() * (p3.getY() - p1.getY()) + 
                                       p3.getX() * (p1.getY() - p2.getY())));
        return Math.abs(area) < 1e-6;
    }
    
    private double calculateDistance(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private boolean isConvexQuadrilateral(Point p1, Point p2, Point p3, Point p4) {
        // A quadrilateral is convex if all interior angles are less than 180 degrees
        // We can check this by ensuring all cross products have the same sign
        
        double[] v12 = {p2.getX() - p1.getX(), p2.getY() - p1.getY()};
        double[] v23 = {p3.getX() - p2.getX(), p3.getY() - p2.getY()};
        double[] v34 = {p4.getX() - p3.getX(), p4.getY() - p3.getY()};
        double[] v41 = {p1.getX() - p4.getX(), p1.getY() - p4.getY()};
        
        double cross1 = crossProduct(v12, v23);
        double cross2 = crossProduct(v23, v34);
        double cross3 = crossProduct(v34, v41);
        double cross4 = crossProduct(v41, v12);
        
        return (cross1 > 0 && cross2 > 0 && cross3 > 0 && cross4 > 0) ||
               (cross1 < 0 && cross2 < 0 && cross3 < 0 && cross4 < 0);
    }
    
    private double crossProduct(double[] v1, double[] v2) {
        return v1[0] * v2[1] - v1[1] * v2[0];
    }
}
