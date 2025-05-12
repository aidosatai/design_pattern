package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.InvalidShapeException;

public interface PerimeterCalculator<T extends Shape> {
    double calculatePerimeter(T shape) throws InvalidShapeException;
}
