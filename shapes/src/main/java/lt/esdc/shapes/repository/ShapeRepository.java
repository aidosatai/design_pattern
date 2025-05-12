package lt.esdc.shapes.repository;

import lt.esdc.shapes.entity.Shape;

import java.util.List;

public interface ShapeRepository<T extends Shape> {
    
    void add(T shape);
    
    boolean remove(String id);
    
    boolean update(T shape);
    
    T getById(String id);
    
    List<T> getAll();
    
    List<T> query(Specification<T> specification);
}
