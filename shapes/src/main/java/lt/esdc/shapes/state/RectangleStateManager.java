package lt.esdc.shapes.state;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;

public class RectangleStateManager {
    
    private static final double EPSILON = 0.0001;
    
    public static RectangleState determineState(Rectangle rectangle) {
        if (isSquare(rectangle)) {
            return new SquareState();
        } else if (isRhombus(rectangle)) {
            return new RhombusState();
        } else if (isTrapezoid(rectangle)) {
            return new TrapezoidState();
        } else {
            return new RegularRectangleState();
        }
    }
    
    private static boolean isSquare(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        double diagonal1 = calculateDistance(p1, p3);
        double diagonal2 = calculateDistance(p2, p4);
        
        return areEqual(side1, side2) && areEqual(side2, side3) &&
               areEqual(side3, side4) && areEqual(side4, side1) &&
               areEqual(diagonal1, diagonal2);
    }
    
    private static boolean isRhombus(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        double side1 = calculateDistance(p1, p2);
        double side2 = calculateDistance(p2, p3);
        double side3 = calculateDistance(p3, p4);
        double side4 = calculateDistance(p4, p1);
        double diagonal1 = calculateDistance(p1, p3);
        double diagonal2 = calculateDistance(p2, p4);
        
        return areEqual(side1, side2) && areEqual(side2, side3) &&
               areEqual(side3, side4) && areEqual(side4, side1) &&
               !areEqual(diagonal1, diagonal2);
    }
    
    private static boolean isTrapezoid(Rectangle rectangle) {
        Point p1 = rectangle.getPoint1();
        Point p2 = rectangle.getPoint2();
        Point p3 = rectangle.getPoint3();
        Point p4 = rectangle.getPoint4();
        
        boolean parallel1 = areLinesParallel(p1, p2, p3, p4);
        boolean parallel2 = areLinesParallel(p1, p4, p2, p3);
        
        return (parallel1 && !parallel2) || (!parallel1 && parallel2);
    }
    
    private static double calculateDistance(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private static boolean areEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
    
    private static boolean areLinesParallel(Point p1, Point p2, Point p3, Point p4) {
        double dx1 = p2.getX() - p1.getX();
        double dy1 = p2.getY() - p1.getY();
        double dx2 = p4.getX() - p3.getX();
        double dy2 = p4.getY() - p3.getY();
        
        return Math.abs(dx1 * dy2 - dx2 * dy1) < EPSILON;
    }
}
