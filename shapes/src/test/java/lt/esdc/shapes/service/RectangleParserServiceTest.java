package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.factory.RectangleFactory;
import lt.esdc.shapes.validator.RectangleValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RectangleParserServiceTest {
    
    private RectangleParserService parserService;
    
    @BeforeMethod
    public void setUp() {
        RectangleFactory factory = new RectangleFactory(new RectangleValidator());
        parserService = new RectangleParserService(factory);
    }
    
    @Test
    public void testParseValidRectangle() {
        // Valid rectangle definition string: "id x1,y1 x2,y2 x3,y3 x4,y4"
        List<String> lines = Collections.singletonList("rect1 0,0 0,2 3,2 3,0");
        
        List<Rectangle> rectangles = parserService.parseRectangles(lines);
        
        assertEquals(1, rectangles.size());
        Rectangle rectangle = rectangles.get(0);
        assertEquals(0, rectangle.getPoint1().getX(), 0.001);
        assertEquals(0, rectangle.getPoint1().getY(), 0.001);
        assertEquals(0, rectangle.getPoint2().getX(), 0.001);
        assertEquals(2, rectangle.getPoint2().getY(), 0.001);
        assertEquals(3, rectangle.getPoint3().getX(), 0.001);
        assertEquals(2, rectangle.getPoint3().getY(), 0.001);
        assertEquals(3, rectangle.getPoint4().getX(), 0.001);
        assertEquals(0, rectangle.getPoint4().getY(), 0.001);
    }
    
    @Test
    public void testParseMultipleValidRectangles() {
        List<String> lines = Arrays.asList(
                "rect1 0,0 0,2 3,2 3,0",
                "rect2 1,1 1,3 4,3 4,1"
        );
        
        List<Rectangle> rectangles = parserService.parseRectangles(lines);
        
        assertEquals(2, rectangles.size());
    }
    
    @Test
    public void testParseInvalidLineFormat() {
        // Invalid line format - missing points
        List<String> lines = Collections.singletonList("rect1 0,0 0,2 3,2");
        
        List<Rectangle> rectangles = parserService.parseRectangles(lines);
        
        assertTrue(rectangles.isEmpty());
    }
    
    @Test
    public void testParseInvalidPointFormat() {
        // Invalid point format - not numbers
        List<String> lines = Collections.singletonList("rect1 0,0 0,2 3,2 invalid,point");
        
        List<Rectangle> rectangles = parserService.parseRectangles(lines);
        
        assertTrue(rectangles.isEmpty());
    }
    
    @Test
    public void testParseEmptyInput() {
        List<Rectangle> rectangles = parserService.parseRectangles(Collections.emptyList());
        
        assertTrue(rectangles.isEmpty());
    }
}
