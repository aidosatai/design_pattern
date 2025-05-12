package lt.esdc.shapes.observer;

import lt.esdc.shapes.entity.Shape;

public interface ShapeObserver {
    void onShapeChanged(String shapeId, Shape shape);
}
