package lt.esdc.shapes;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.factory.RectangleFactory;
import lt.esdc.shapes.repository.RectangleRepository;
import lt.esdc.shapes.repository.specification.ConvexSpecification;
import lt.esdc.shapes.repository.specification.PerimeterRangeSpecification;
import lt.esdc.shapes.repository.specification.RhombusSpecification;
import lt.esdc.shapes.repository.specification.SquareSpecification;
import lt.esdc.shapes.repository.specification.TrapezoidSpecification;
import lt.esdc.shapes.service.RectangleParserService;
import lt.esdc.shapes.service.RectangleServiceInterface;
import lt.esdc.shapes.service.impl.RectangleServiceImpl;
import lt.esdc.shapes.util.RectangleReader;
import lt.esdc.shapes.validator.RectangleValidator;
import lt.esdc.shapes.warehouse.ShapeWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShapeApplication {
    private static final Logger LOGGER = LogManager.getLogger(ShapeApplication.class);
    private static final String RECTANGLES_FILE = "src/main/resources/data/rectangles.txt";

    public static void main(String[] args) {
        LOGGER.info("Starting Shapes Application");

        ShapeWarehouse warehouse = ShapeWarehouse.getInstance();
        RectangleServiceInterface service = new RectangleServiceImpl(warehouse);
        RectangleRepository repository = new RectangleRepository();
        
        warehouse.setRectangleService(service);
        
        RectangleReader reader = new RectangleReader();
        List<String> lines = reader.readLinesFromFile(RECTANGLES_FILE);
        
        RectangleFactory factory = new RectangleFactory(new RectangleValidator());
        RectangleParserService parserService = new RectangleParserService(factory);
        List<Rectangle> rectangles = parserService.parseRectangles(lines);
        
        LOGGER.info("Read {} valid rectangles from file", rectangles.size());
        
        // Add rectangles to repository
        for (Rectangle rectangle : rectangles) {
            repository.add(rectangle);
            
            // Calculate and store properties
            double perimeter = rectangle.calculatePerimeter();
            double area = rectangle.calculateArea();
            
            LOGGER.info("Rectangle {}: Perimeter = {}, Area = {}", 
                    rectangle.getId(), perimeter, area);
            
            // Check rectangle type
            if (service.isSquare(rectangle)) {
                LOGGER.info("Rectangle {} is a square", rectangle.getId());
            } else if (service.isRhombus(rectangle)) {
                LOGGER.info("Rectangle {} is a rhombus", rectangle.getId());
            } else if (service.isTrapezoid(rectangle)) {
                LOGGER.info("Rectangle {} is a trapezoid", rectangle.getId());
            }
            
            if (service.isConvex(rectangle)) {
                LOGGER.info("Rectangle {} is convex", rectangle.getId());
            } else {
                LOGGER.info("Rectangle {} is not convex", rectangle.getId());
            }
        }
        
        // Demonstrate specification pattern
        SquareSpecification squareSpec = new SquareSpecification(service);
        RhombusSpecification rhombusSpec = new RhombusSpecification(service);
        TrapezoidSpecification trapezoidSpec = new TrapezoidSpecification(service);
        ConvexSpecification convexSpec = new ConvexSpecification(service);
        PerimeterRangeSpecification perimeterSpec = new PerimeterRangeSpecification(service, 20.0, 40.0);
        
        List<Rectangle> squares = repository.query(squareSpec);
        List<Rectangle> rhombuses = repository.query(rhombusSpec);
        List<Rectangle> trapezoids = repository.query(trapezoidSpec);
        List<Rectangle> convexRectangles = repository.query(convexSpec);
        List<Rectangle> mediumPerimeterRectangles = repository.query(perimeterSpec);
        
        LOGGER.info("Number of squares: {}", squares.size());
        LOGGER.info("Number of rhombuses: {}", rhombuses.size());
        LOGGER.info("Number of trapezoids: {}", trapezoids.size());
        LOGGER.info("Number of convex rectangles: {}", convexRectangles.size());
        LOGGER.info("Number of rectangles with perimeter between 20 and 40: {}", 
                mediumPerimeterRectangles.size());
        
        // Demonstrate sorting
        List<Rectangle> sortedByPerimeter = repository.sortByPerimeter(service);
        LOGGER.info("Rectangles sorted by perimeter:");
        for (Rectangle rectangle : sortedByPerimeter) {
            LOGGER.info("Rectangle {}: Perimeter = {}", 
                    rectangle.getId(), service.calculatePerimeter(rectangle));
        }
        
        // Demonstrate observer
        if (!rectangles.isEmpty()) {
            Rectangle rectangle = rectangles.get(0);
            double oldX = rectangle.getPoint1().getX();
            double oldY = rectangle.getPoint1().getY();
            
            LOGGER.info("Before modification - Rectangle {}: Point1 = ({}, {}), Perimeter = {}, Area = {}", 
                    rectangle.getId(), oldX, oldY, 
                    warehouse.getPerimeter(rectangle.getId()), 
                    warehouse.getArea(rectangle.getId()));
            
            // Modify the rectangle
            rectangle.getPoint1().setX(oldX + 1.0);
            rectangle.getPoint1().setY(oldY + 1.0);
            
            LOGGER.info("After modification - Rectangle {}: Point1 = ({}, {}), Perimeter = {}, Area = {}", 
                    rectangle.getId(), rectangle.getPoint1().getX(), rectangle.getPoint1().getY(), 
                    warehouse.getPerimeter(rectangle.getId()), 
                    warehouse.getArea(rectangle.getId()));
        }
        
        LOGGER.info("Shapes Application completed");
    }
}
