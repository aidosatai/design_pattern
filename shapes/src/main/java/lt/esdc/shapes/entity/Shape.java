package lt.esdc.shapes.entity;

import lt.esdc.shapes.observer.ShapeObserver;

public abstract class Shape {
    private String id;
    private ShapeObserver observer;

    public Shape(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObserver(ShapeObserver observer) {
        this.observer = observer;
    }

    protected void notifyObserver() {
        if (observer != null) observer.onShapeChanged(this.id, this);
    }

    public abstract double calculateArea();
    public abstract double calculatePerimeter();
}
