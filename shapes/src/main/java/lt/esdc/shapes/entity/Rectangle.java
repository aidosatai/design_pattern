package lt.esdc.shapes.entity;

import lt.esdc.shapes.service.impl.RectangleAreaCalculator;
import lt.esdc.shapes.service.impl.RectanglePerimeterCalculator;
import lt.esdc.shapes.state.RectangleState;
import lt.esdc.shapes.state.RectangleStateManager;
import lt.esdc.shapes.state.RegularRectangleState;

public class Rectangle extends Shape {

    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
    private RectangleState state;
    
    public Rectangle(String id, Point point1, Point point2, Point point3, Point point4) {
        super(id);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        this.state = RectangleStateManager.determineState(this);
    }
    
    public Point getPoint1() {
        return point1;
    }
    
    public void setPoint1(Point point1) {
        this.point1 = point1;
        updateState();
        notifyObserver();
    }
    
    public Point getPoint2() {
        return point2;
    }
    
    public void setPoint2(Point point2) {
        this.point2 = point2;
        updateState();
        notifyObserver();
    }
    
    public Point getPoint3() {
        return point3;
    }
    
    public void setPoint3(Point point3) {
        this.point3 = point3;
        updateState();
        notifyObserver();
    }
    
    public Point getPoint4() {
        return point4;
    }
    
    public void setPoint4(Point point4) {
        this.point4 = point4;
        updateState();
        notifyObserver();
    }
    
    public RectangleState getState() {
        return state;
    }
    
    public void setState(RectangleState state) {
        this.state = state;
    }
    
    private void updateState() {
        this.state = RectangleStateManager.determineState(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Rectangle rectangle = (Rectangle) o;
        
        if (!getId().equals(rectangle.getId())) return false;
        if (!point1.equals(rectangle.point1)) return false;
        if (!point2.equals(rectangle.point2)) return false;
        if (!point3.equals(rectangle.point3)) return false;
        return point4.equals(rectangle.point4);
    }
    
    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + point1.hashCode();
        result = 31 * result + point2.hashCode();
        result = 31 * result + point3.hashCode();
        result = 31 * result + point4.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "Rectangle{" +
                "id='" + getId() + '\'' +
                ", point1=" + point1 +
                ", point2=" + point2 +
                ", point3=" + point3 +
                ", point4=" + point4 +
                ", state=" + state.getStateName() +
                '}';
    }
    
    @Override
    public double calculateArea() {
        // Вычисление площади прямоугольника по формуле Гаусса для произвольного четырехугольника
        double area = 0.5 * Math.abs(
            (point1.getX() * point2.getY() - point2.getX() * point1.getY()) +
            (point2.getX() * point3.getY() - point3.getX() * point2.getY()) +
            (point3.getX() * point4.getY() - point4.getX() * point3.getY()) +
            (point4.getX() * point1.getY() - point1.getX() * point4.getY())
        );
        return area;
    }
    
    @Override
    public double calculatePerimeter() {
        // Вычисление периметра как суммы длин всех сторон
        double side1 = calculateDistance(point1, point2);
        double side2 = calculateDistance(point2, point3);
        double side3 = calculateDistance(point3, point4);
        double side4 = calculateDistance(point4, point1);
        return side1 + side2 + side3 + side4;
    }
    
    private double calculateDistance(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
