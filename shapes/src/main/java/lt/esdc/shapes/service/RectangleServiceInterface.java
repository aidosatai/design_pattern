package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Rectangle;

/**
 * Interface for Rectangle service operations.
 * Extends the base ShapeService with rectangle-specific functionality.
 */
public interface RectangleServiceInterface extends ShapeService<Rectangle> {
    /**
     * Checks if the rectangle is valid.
     *
     * @param rectangle the rectangle to check
     * @return true if the rectangle is valid, false otherwise
     */
    boolean isValidRectangle(Rectangle rectangle);
    
    /**
     * Checks if the rectangle is convex.
     *
     * @param rectangle the rectangle to check
     * @return true if the rectangle is convex, false otherwise
     */
    boolean isConvex(Rectangle rectangle);
    
    /**
     * Checks if the rectangle is a square.
     *
     * @param rectangle the rectangle to check
     * @return true if the rectangle is a square, false otherwise
     */
    boolean isSquare(Rectangle rectangle);
    
    /**
     * Checks if the rectangle is a rhombus.
     *
     * @param rectangle the rectangle to check
     * @return true if the rectangle is a rhombus, false otherwise
     */
    boolean isRhombus(Rectangle rectangle);
    
    /**
     * Checks if the rectangle is a trapezoid.
     *
     * @param rectangle the rectangle to check
     * @return true if the rectangle is a trapezoid, false otherwise
     */
    boolean isTrapezoid(Rectangle rectangle);
}
