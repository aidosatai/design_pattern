package lt.esdc.shapes.repository.specification;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.repository.Specification;
import lt.esdc.shapes.service.RectangleServiceInterface;

public class TrapezoidSpecification implements Specification<Rectangle> {

    private final RectangleServiceInterface rectangleService;

    public TrapezoidSpecification(RectangleServiceInterface rectangleService) {
        this.rectangleService = rectangleService;
    }

    @Override
    public boolean isSatisfiedBy(Rectangle rectangle) {
        return rectangleService.isTrapezoid(rectangle);
    }
}
