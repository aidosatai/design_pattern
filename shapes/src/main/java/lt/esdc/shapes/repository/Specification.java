package lt.esdc.shapes.repository;

/**
 * Interface for the Specification pattern.
 * Allows combining specifications using and/or operations.
 *
 * @param <T> Type of object to check against the specification
 */
public interface Specification<T> {
    /**
     * Checks if the item satisfies the specification.
     *
     * @param item Item to check
     * @return true if the item satisfies the specification, false otherwise
     */
    boolean isSatisfiedBy(T item);
    
    /**
     * Combines this specification with another one using AND logic.
     *
     * @param other Other specification to combine with
     * @return A new specification that is satisfied when both specifications are satisfied
     */
    default Specification<T> and(Specification<T> other) {
        return item -> this.isSatisfiedBy(item) && other.isSatisfiedBy(item);
    }
    
    /**
     * Combines this specification with another one using OR logic.
     *
     * @param other Other specification to combine with
     * @return A new specification that is satisfied when either specification is satisfied
     */
    default Specification<T> or(Specification<T> other) {
        return item -> this.isSatisfiedBy(item) || other.isSatisfiedBy(item);
    }
    
    /**
     * Negates this specification.
     *
     * @return A new specification that is satisfied when this specification is not satisfied
     */
    default Specification<T> not() {
        return item -> !this.isSatisfiedBy(item);
    }
}
