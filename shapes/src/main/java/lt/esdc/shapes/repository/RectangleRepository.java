package lt.esdc.shapes.repository;

import lt.esdc.shapes.entity.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return rectangles.get(id);
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
    
    public List<Rectangle> sortByPerimeter(lt.esdc.shapes.service.RectangleService service) {
        return rectangles.values().stream()
                .sorted(Comparator.comparingDouble(service::calculatePerimeter))
                .collect(Collectors.toList());
    }
    
    public List<Rectangle> sortByArea(lt.esdc.shapes.service.RectangleService service) {
        return rectangles.values().stream()
                .sorted(Comparator.comparingDouble(service::calculateArea))
                .collect(Collectors.toList());
    }
}
