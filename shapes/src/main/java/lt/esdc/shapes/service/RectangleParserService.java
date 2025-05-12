package lt.esdc.shapes.service;

import lt.esdc.shapes.entity.Rectangle;
import lt.esdc.shapes.exception.InvalidShapeException;
import lt.esdc.shapes.entity.Point;
import lt.esdc.shapes.factory.RectangleFactory;
import lt.esdc.shapes.factory.ShapeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RectangleParserService {
    private static final Logger LOGGER = LogManager.getLogger(RectangleParserService.class);
    private final RectangleFactory rectangleFactory;
    
    public RectangleParserService(RectangleFactory rectangleFactory) {
        this.rectangleFactory = rectangleFactory;
    }

    public List<Rectangle> parseRectangles(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return new ArrayList<>();
        }

        return lines.stream()
                .map(this::parseRectangle)
                .filter(rectangle -> rectangle != null)
                .collect(Collectors.toList());
    }

    private Rectangle parseRectangle(String line) {
        try {
            String[] parts = line.split("\\s+");
            if (parts.length < 5) { // Нужно id и 4 точки
                LOGGER.warn("Invalid rectangle format, need ID and 4 points: {}", line);
                return null;
            }
            
            // Парсим точки в формате "x,y"
            Point[] points = new Point[4];
            for (int i = 0; i < 4; i++) {
                String[] coordinates = parts[i+1].split(",");
                if (coordinates.length != 2) {
                    LOGGER.warn("Invalid point format at position {}: {}", i+1, parts[i+1]);
                    return null;
                }
                try {
                    double x = Double.parseDouble(coordinates[0]);
                    double y = Double.parseDouble(coordinates[1]);
                    points[i] = new Point(x, y);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Invalid coordinate format: {}", parts[i+1], e);
                    return null;
                }
            }
            
            // Создаем прямоугольник через фабрику
            String id = parts[0]; // ID из строки данных
            return (Rectangle) rectangleFactory.createShape(
                id, 
                String.valueOf(points[0].getX()), String.valueOf(points[0].getY()),
                String.valueOf(points[1].getX()), String.valueOf(points[1].getY()),
                String.valueOf(points[2].getX()), String.valueOf(points[2].getY()),
                String.valueOf(points[3].getX()), String.valueOf(points[3].getY()));
        } catch (InvalidShapeException e) {
            LOGGER.warn("Invalid rectangle data: {}", line, e);
            return null;
        }
    }
}
