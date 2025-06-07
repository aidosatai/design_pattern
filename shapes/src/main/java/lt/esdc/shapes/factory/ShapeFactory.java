package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.InvalidShapeException;

/**
 * Abstract factory for creating shape objects.
 * This class defines the contract for the Factory Method pattern.
 */
public abstract class ShapeFactory {
    
    /**
     * Creates a shape with the given points.
     * This is the factory method that concrete factories must implement.
     *
     * @param id Optional ID for the shape. If null, an ID should be generated.
     * @param points Array of points that define the shape
     * @return A new shape instance
     * @throws InvalidShapeException if the shape is invalid
     */
    public abstract Shape createShape(String id, Point... points) throws InvalidShapeException;
    
    /**
     * Creates a shape with an auto-generated ID and the given points.
     *
     * @param points Array of points that define the shape
     * @return A new shape instance
     * @throws InvalidShapeException if the shape is invalid
     */
    public Shape createShape(Point... points) throws InvalidShapeException {
        return createShape(null, points);
    }
}
