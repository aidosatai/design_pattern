package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.util.IDGenerator;
import lt.esdc.shapes.validator.RectangleValidator;
import lt.esdc.shapes.validator.ValidationResult;

/**
 * Concrete factory for creating Rectangle objects.
 * Implements the Factory Method pattern.
 */
public class RectangleFactory extends ShapeFactory {
    private final RectangleValidator validator;

    /**
     * Creates a new RectangleFactory with the given validator.
     *
     * @param validator The validator for rectangle objects
     */
    public RectangleFactory(RectangleValidator validator) {
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     * Creates a Rectangle using the provided points.
     * 
     * @param id Optional ID for the rectangle. If null, an ID will be generated.
     * @param points An array of exactly 4 points defining the rectangle
     * @return A new Rectangle instance
     * @throws InvalidShapeException if the points do not form a valid rectangle or if fewer than 4 points are provided
     */
    @Override
    public Shape createShape(String id, Point... points) throws InvalidShapeException {
        // Validate the number of points
        if (points == null || points.length != 4) {
            throw new InvalidShapeException("Exactly 4 points must be provided for Rectangle creation");
        }
        
        // Validate each point is not null
        for (Point point : points) {
            if (point == null) {
                throw new InvalidShapeException("All points must be provided for Rectangle creation (null point detected)");
            }
        }

        // Generate ID if not provided
        String rectangleId = (id != null) ? id : IDGenerator.generateId();

        // Create the rectangle
        Rectangle rectangle = new Rectangle(rectangleId, points[0], points[1], points[2], points[3]);

        // Validate the rectangle geometry
        ValidationResult validationResult = validator.validate(rectangle);
        if (!validationResult.isValid()) {
            throw new InvalidShapeException("Invalid rectangle: " + validationResult.getErrorMessage());
        }

        return rectangle;
    }
    
    /**
     * Specific method for creating rectangles from 4 specific points.
     * This method is provided for convenience and backward compatibility.
     *
     * @param id Optional ID for the rectangle. If null, an ID will be generated.
     * @param point1 First point of the rectangle
     * @param point2 Second point of the rectangle
     * @param point3 Third point of the rectangle
     * @param point4 Fourth point of the rectangle
     * @return A new Rectangle instance
     * @throws InvalidShapeException if the points do not form a valid rectangle
     */
    public Shape createRectangle(String id, Point point1, Point point2, Point point3, Point point4) throws InvalidShapeException {
        return createShape(id, point1, point2, point3, point4);
    }
}
