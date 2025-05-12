package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.util.IDGenerator;
import lt.esdc.shapes.validator.RectangleValidator;
import lt.esdc.shapes.validator.ValidationResult;

public class ShapeFactory {
    public enum ShapeType {
        RECTANGLE
    }
    
    private static final RectangleValidator rectangleValidator = new RectangleValidator();
    
    public static Shape createShape(ShapeType type, Point point1, Point point2, Point point3, Point point4) throws InvalidShapeException {
        switch (type) {
            case RECTANGLE:
                Rectangle rectangle = new Rectangle(null, point1, point2, point3, point4);
                ValidationResult validation = rectangleValidator.validate(rectangle);
                if (!validation.isValid()) {
                    throw new InvalidShapeException(validation.getErrorMessage());
                }
                String id = IDGenerator.generateId();
                rectangle.setId(id);
                return rectangle;
            default:
                throw new InvalidShapeException("Illegal shape type: " + type);
        }
    }
}
