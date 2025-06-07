package lt.esdc.shapes.repository.specification;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.repository.Specification;
import lt.esdc.shapes.service.RectangleServiceInterface;

public class SquareSpecification implements Specification<Rectangle> {

    private final RectangleServiceInterface rectangleService;

    public SquareSpecification(RectangleServiceInterface rectangleService) {
        this.rectangleService = rectangleService;
    }

    @Override
    public boolean isSatisfiedBy(Rectangle rectangle) {
        return rectangleService.isSquare(rectangle);
    }
}
