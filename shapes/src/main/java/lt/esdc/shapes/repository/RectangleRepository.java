package lt.esdc.shapes.repository;

import lt.esdc.shapes.entity.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lt.esdc.shapes.service.RectangleServiceInterface;

/**
 * Concrete implementation of a repository for Rectangle objects.
 */
public class RectangleRepository implements ShapeRepository<Rectangle> {
    
    private final Map<String, Rectangle> rectangles;
    
    public RectangleRepository() {
        this.rectangles = new HashMap<>();
    }
    
    @Override
    public void add(Rectangle rectangle) {
        if (rectangle == null || rectangle.getId() == null) {
            return;
        }
        rectangles.put(rectangle.getId(), rectangle);
    }
    
    @Override
    public boolean remove(String id) {
        return rectangles.remove(id) != null;
    }
    
    @Override
    public boolean update(Rectangle rectangle) {
        if (rectangle == null || rectangle.getId() == null) {
            return false;
        }
        if (!rectangles.containsKey(rectangle.getId())) {
            return false;
        }
        rectangles.put(rectangle.getId(), rectangle);
        return true;
    }
    
    @Override
    public Rectangle getById(String id) {
        Rectangle original = rectangles.get(id);
        if (original == null) {
            return null;
        }
        // Create a deep copy to preserve encapsulation
        return new Rectangle(
            original.getId(),
            new lt.esdc.shapes.entity.Point(original.getPoint1().getX(), original.getPoint1().getY()),
            new lt.esdc.shapes.entity.Point(original.getPoint2().getX(), original.getPoint2().getY()),
            new lt.esdc.shapes.entity.Point(original.getPoint3().getX(), original.getPoint3().getY()),
            new lt.esdc.shapes.entity.Point(original.getPoint4().getX(), original.getPoint4().getY())
        );
    }
    
    @Override
    public List<Rectangle> getAll() {
        return new ArrayList<>(rectangles.values());
    }
    
    @Override
    public List<Rectangle> query(Specification<Rectangle> specification) {
        return rectangles.values().stream()
                .filter(specification::isSatisfiedBy)
                .collect(Collectors.toList());
    }
    
    public List<Rectangle> sortById() {
        return rectangles.values().stream()
                .sorted(Comparator.comparing(Rectangle::getId))
                .collect(Collectors.toList());
    }
    
    /**
     * Sort rectangles by perimeter in ascending order.
     *
     * @param service Service to calculate the perimeter
     * @return A list of rectangles sorted by perimeter
     */
    public List<Rectangle> sortByPerimeter(RectangleServiceInterface service) {
        return rectangles.values().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(service::calculatePerimeter))
                .collect(Collectors.toList());
    }
    
    /**
     * Sort rectangles by area in ascending order.
     *
     * @param service Service to calculate the area
     * @return A list of rectangles sorted by area
     */
    public List<Rectangle> sortByArea(RectangleServiceInterface service) {
        return rectangles.values().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(service::calculateArea))
                .collect(Collectors.toList());
    }
}
