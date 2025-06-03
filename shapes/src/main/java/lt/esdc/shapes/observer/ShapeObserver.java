package lt.esdc.shapes.observer;

import lt.esdc.shapes.entity.Shape;

/**
 * Observer interface for shapes.
 *
 * <p>The tests in this project expect observers to receive only the
 * updated shape instance.  Previously the interface provided the shape
 * identifier as a separate argument.  This mismatched the tests and lead
 * to compilation errors.  The method signature has been adjusted to
 * match the expectation.</p>
 */
public interface ShapeObserver {
    /**
     * Called when a shape changes.
     *
     * @param shape the shape that has been modified
     */
    void update(Shape shape);
}
