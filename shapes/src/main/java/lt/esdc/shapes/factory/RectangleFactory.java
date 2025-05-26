package lt.esdc.shapes.factory;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.entity.Shape;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.util.IDGenerator;
import lt.esdc.shapes.validator.RectangleValidator;
import lt.esdc.shapes.validator.ValidationResult;

public class RectangleFactory extends ShapeFactory {
    private static final int REQUIRED_PARAMETERS_COUNT = 8; 
    private final RectangleValidator validator;

    public RectangleFactory(RectangleValidator validator) {
        this.validator = validator;
    }

    public Shape createShape(String id, String... parameters) throws InvalidShapeException {
        if (parameters == null || parameters.length != REQUIRED_PARAMETERS_COUNT) {
            throw new InvalidShapeException("Invalid number of parameters for Rectangle creation");
        }

        try {
            double x1 = Double.parseDouble(parameters[0]);
            double y1 = Double.parseDouble(parameters[1]);
            double x2 = Double.parseDouble(parameters[2]);
            double y2 = Double.parseDouble(parameters[3]);
            double x3 = Double.parseDouble(parameters[4]);
            double y3 = Double.parseDouble(parameters[5]);
            double x4 = Double.parseDouble(parameters[6]);
            double y4 = Double.parseDouble(parameters[7]);

            Point point1 = new Point(x1, y1);
            Point point2 = new Point(x2, y2);
            Point point3 = new Point(x3, y3);
            Point point4 = new Point(x4, y4);
            
            String rectangleId = (id != null) ? id : IDGenerator.generateId();

            Rectangle rectangle = new Rectangle(rectangleId, point1, point2, point3, point4);
            
            ValidationResult validationResult = validator.validate(rectangle);
            if (!validationResult.isValid()) {
                throw new InvalidShapeException("Invalid rectangle: " + validationResult.getErrorMessage());
            }
            
            return rectangle;
        } catch (NumberFormatException e) {
            throw new InvalidShapeException("Invalid parameter format for Rectangle creation", e);
        }
    }
}
