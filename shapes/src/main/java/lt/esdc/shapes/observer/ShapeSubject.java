package lt.esdc.shapes.observer;

import lt.esdc.shapes.entity.Shape;

import java.util.ArrayList;
import java.util.List;

public class ShapeSubject {
    
    private final List<ShapeObserver> observers;
    
    public ShapeSubject() {
        this.observers = new ArrayList<>();
    }
    
    public void addObserver(ShapeObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    public void removeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(Shape shape) {
        for (ShapeObserver observer : observers) {
            observer.update(shape);
        }
    }
}
