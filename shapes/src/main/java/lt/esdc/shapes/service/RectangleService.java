package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.warehouse.ShapeWarehouse;

public class RectangleService implements ShapeService<Rectangle> {
    
    private final ShapeWarehouse warehouse;
    
    public RectangleService(ShapeWarehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    @Override
    public double calculatePerimeter(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        
        double perimeter = side1 + side2 + side3 + side4;
        
        // Store the calculated perimeter in the warehouse
        warehouse.storePerimeter(rectangle.getId(), perimeter);
        
        return perimeter;
    }
    
    @Override
    public double calculateArea(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // For a rectangle, we can calculate the area as width * height
        // We'll use the Shoelace formula to calculate the area of the quadrilateral
        double area = 0.5 * Math.abs(
            (p1.getX() * p2.getY() - p2.getX() * p1.getY()) +
            (p2.getX() * p3.getY() - p3.getX() * p2.getY()) +
            (p3.getX() * p4.getY() - p4.getX() * p3.getY()) +
            (p4.getX() * p1.getY() - p1.getX() * p4.getY())
        );
        
        // Store the calculated area in the warehouse
        warehouse.storeArea(rectangle.getId(), area);
        
        return area;
    }
    
    public boolean isValidRectangle(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Calculate sides and diagonals
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        double diagonal1 = calculateDistance(p1, p3);
        double diagonal2 = calculateDistance(p2, p4);
        
        // Check if opposite sides are equal and diagonals are equal
        boolean oppositeSidesEqual = Math.abs(side1 - side3) < 1e-6 && Math.abs(side2 - side4) < 1e-6;
        boolean diagonalsEqual = Math.abs(diagonal1 - diagonal2) < 1e-6;
        
        return oppositeSidesEqual && diagonalsEqual;
    }
    
    public boolean isConvex(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Vectors for edges
        double[] v12 = {p2.getX() - p1.getX(), p2.getY() - p1.getY()};
        double[] v23 = {p3.getX() - p2.getX(), p3.getY() - p2.getY()};
        double[] v34 = {p4.getX() - p3.getX(), p4.getY() - p3.getY()};
        double[] v41 = {p1.getX() - p4.getX(), p1.getY() - p4.getY()};
        
        // Cross products
        double cross1 = crossProduct(v12, v23);
        double cross2 = crossProduct(v23, v34);
        double cross3 = crossProduct(v34, v41);
        double cross4 = crossProduct(v41, v12);
        
        // Check if all cross products have the same sign
        return (cross1 > 0 && cross2 > 0 && cross3 > 0 && cross4 > 0) ||
               (cross1 < 0 && cross2 < 0 && cross3 < 0 && cross4 < 0);
    }
    
    public boolean isSquare(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Calculate sides and diagonals
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        double diagonal1 = calculateDistance(p1, p3);
        double diagonal2 = calculateDistance(p2, p4);
        
        // A square has all sides equal and all angles equal to 90 degrees
        // Since all angles are 90 degrees, the diagonals are equal
        boolean allSidesEqual = Math.abs(side1 - side2) < 1e-6 && 
                               Math.abs(side2 - side3) < 1e-6 && 
                               Math.abs(side3 - side4) < 1e-6;
        boolean diagonalsEqual = Math.abs(diagonal1 - diagonal2) < 1e-6;
        
        return allSidesEqual && diagonalsEqual;
    }
    
    public boolean isRhombus(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Calculate sides
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        
        // Check if all sides are equal
        boolean allSidesEqual = Math.abs(side1 - side2) < 1e-6 && 
                               Math.abs(side2 - side3) < 1e-6 && 
                               Math.abs(side3 - side4) < 1e-6;
        
        return allSidesEqual && isConvex(rectangle);
    }
    
    public boolean isTrapezoid(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        // Check if opposite sides are parallel
        boolean side12ParallelToSide34 = areLinesParallel(p1, p2, p3, p4);
        boolean side23ParallelToSide41 = areLinesParallel(p2, p3, p4, p1);
        
        // A trapezoid has exactly one pair of parallel sides
        return (side12ParallelToSide34 && !side23ParallelToSide41) || 
               (!side12ParallelToSide34 && side23ParallelToSide41);
    }
    
    private double calculateDistance(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private double crossProduct(double[] v1, double[] v2) {
        return v1[0] * v2[1] - v1[1] * v2[0];
    }
    
    private boolean areLinesParallel(Point p1, Point p2, Point p3, Point p4) {
        // Calculate slopes
        double dx1 = p2.getX() - p1.getX();
        double dy1 = p2.getY() - p1.getY();
        double dx2 = p4.getX() - p3.getX();
        double dy2 = p4.getY() - p3.getY();
        
        // If both lines are vertical
        if (Math.abs(dx1) < 1e-6 && Math.abs(dx2) < 1e-6) {
            return true;
        }
        
        // If one line is vertical and the other is not
        if (Math.abs(dx1) < 1e-6 || Math.abs(dx2) < 1e-6) {
            return false;
        }
        
        // Compare slopes
        double slope1 = dy1 / dx1;
        double slope2 = dy2 / dx2;
        
        return Math.abs(slope1 - slope2) < 1e-6;
    }
}
