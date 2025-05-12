package lt.esdc.shapes.repository;

public interface Specification<T> {
    boolean isSatisfiedBy(T item);
}
