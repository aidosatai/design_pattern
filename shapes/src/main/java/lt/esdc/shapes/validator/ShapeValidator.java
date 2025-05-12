package lt.esdc.shapes.validator;

import lt.esdc.shapes.entity.Shape;

public interface Validator<T extends Shape> {
    ValidationResult validate(T shape);
}
