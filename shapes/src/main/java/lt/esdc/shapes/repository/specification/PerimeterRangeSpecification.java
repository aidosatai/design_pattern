package lt.esdc.shapes.repository.specification;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.repository.Specification;
import lt.esdc.shapes.service.RectangleService;

public class PerimeterRangeSpecification implements Specification<Rectangle> {

    private final RectangleService rectangleService;
    private final double minPerimeter;
    private final double maxPerimeter;

    public PerimeterRangeSpecification(RectangleService rectangleService, double minPerimeter, double maxPerimeter) {
        this.rectangleService = rectangleService;
        this.minPerimeter = minPerimeter;
        this.maxPerimeter = maxPerimeter;
    }

    @Override
    public boolean isSatisfiedBy(Rectangle rectangle) {
        double perimeter = rectangleService.calculatePerimeter(rectangle);
        return perimeter >= minPerimeter && perimeter <= maxPerimeter;
    }
}
