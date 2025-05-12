package lt.esdc.shapes.validator;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class RectangleValidatorTest {
    
    private RectangleValidator validator;
    
    @BeforeMethod
    public void setUp() {
        validator = new RectangleValidator();
    }
    
    @Test
    public void testValidRectangle() {
        // Regular rectangle
        Rectangle rectangle = new Rectangle(
                "test-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(3, 2),
                new Point(3, 0)
        );
        
        ValidationResult result = validator.validate(rectangle);
        assertTrue(result.isValid());
    }
    
    @Test
    public void testValidSquare() {
        // Square
        Rectangle square = new Rectangle(
                "square-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(2, 2),
                new Point(2, 0)
        );
        
        ValidationResult result = validator.validate(square);
        assertTrue(result.isValid());
    }
    
    @Test
    public void testInvalidNull() {
        ValidationResult result = validator.validate(null);
        assertFalse(result.isValid());
        assertEquals("Rectangle cannot be null", result.getErrorMessage());
    }
    
    @Test
    public void testInvalidThreePointsOnSameLine() {
        // Three points on the same line
        Rectangle invalid = new Rectangle(
                "invalid-id",
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 0)
        );
        
        ValidationResult result = validator.validate(invalid);
        assertFalse(result.isValid());
    }
    
    @Test
    public void testInvalidNonConvexQuadrilateral() {
        // Non-convex quadrilateral (self-intersecting)
        Rectangle invalid = new Rectangle(
                "nonconvex-id",
                new Point(0, 0),
                new Point(2, 2),
                new Point(0, 2),
                new Point(2, 0)
        );
        
        ValidationResult result = validator.validate(invalid);
        assertFalse(result.isValid());
    }
}
