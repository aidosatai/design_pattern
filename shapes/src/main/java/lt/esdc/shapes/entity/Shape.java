package lt.esdc.shapes.entity;

import lt.esdc.shapes.observer.ShapeSubject;

/**
 * Base class for all shapes.
 *
 * <p>It now extends {@link ShapeSubject} so that each shape can have
 * multiple observers.  Previous implementation supported only a single
 * observer which did not align with the tests.</p>
 */
public abstract class Shape extends ShapeSubject {
    private String id;

    public Shape(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    // ID is now immutable after creation
    // Setter method removed to enforce immutability

    protected void notifyObserver() {
        notifyObservers(this);
    }

    public abstract double calculateArea();
    public abstract double calculatePerimeter();
}
