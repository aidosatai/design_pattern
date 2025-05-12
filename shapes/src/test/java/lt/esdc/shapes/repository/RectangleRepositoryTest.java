package lt.esdc.shapes.repository;

import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.repository.specification.SquareSpecification;
import lt.esdc.shapes.state.SquareState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RectangleRepositoryTest {
    
    private RectangleRepository repository;
    private Rectangle rectangle;
    private Rectangle square;
    
    @BeforeMethod
    public void setUp() {
        repository = new RectangleRepository();
        
        rectangle = new Rectangle(
                "rect-id",
                new Point(0, 0),
                new Point(0, 3),
                new Point(4, 3),
                new Point(4, 0)
        );
        
        square = new Rectangle(
                "square-id",
                new Point(0, 0),
                new Point(0, 2),
                new Point(2, 2),
                new Point(2, 0)
        );
        square.setState(new SquareState());
        
        repository.add(rectangle);
        repository.add(square);
    }
    
    @Test
    public void testFindAll() {
        List<Rectangle> allRectangles = repository.findAll();
        
        assertEquals(2, allRectangles.size());
        assertTrue(allRectangles.contains(rectangle));
        assertTrue(allRectangles.contains(square));
    }
    
    @Test
    public void testFindById() {
        Rectangle found = repository.findById("rect-id");
        
        assertEquals(rectangle, found);
    }
    
    @Test
    public void testFindByNonExistentId() {
        Rectangle found = repository.findById("non-existent-id");
        
        assertEquals(null, found);
    }
    
    @Test
    public void testFindBySpecification() {
        List<Rectangle> squares = repository.findBySpecification(new SquareSpecification());
        
        assertEquals(1, squares.size());
        assertEquals(square, squares.get(0));
    }
    
    @Test
    public void testRemove() {
        repository.remove(rectangle);
        
        List<Rectangle> allRectangles = repository.findAll();
        assertEquals(1, allRectangles.size());
        assertEquals(square, allRectangles.get(0));
    }
}
