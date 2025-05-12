package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Shape;

public interface ShapeService<T extends Shape> {
    double calculatePerimeter(T shape);
    double calculateArea(T shape);
}
