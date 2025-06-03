package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.util.IDGenerator;
import lt.esdc.shapes.validator.RectangleValidator;
import lt.esdc.shapes.validator.ValidationResult;

public class RectangleFactory extends ShapeFactory {
    private final RectangleValidator validator;

    public RectangleFactory(RectangleValidator validator) {
        this.validator = validator;
    }

    public Shape createShape(String id,
                             Point point1,
                             Point point2,
                             Point point3,
                             Point point4) throws InvalidShapeException {
        if (point1 == null || point2 == null || point3 == null || point4 == null) {
            throw new InvalidShapeException("All points must be provided for Rectangle creation");
        }

        String rectangleId = (id != null) ? id : IDGenerator.generateId();

        Rectangle rectangle = new Rectangle(rectangleId, point1, point2, point3, point4);

        ValidationResult validationResult = validator.validate(rectangle);
        if (!validationResult.isValid()) {
            throw new InvalidShapeException("Invalid rectangle: " + validationResult.getErrorMessage());
        }

        return rectangle;
    }
}
