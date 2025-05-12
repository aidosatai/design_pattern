package lt.esdc.shapes.util;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RectangleReaderTest {
    
    private RectangleReader reader;
    private Path testFilePath;
    
    @BeforeMethod
    public void setUp() throws IOException {
        reader = new RectangleReader();
        
        // Create temporary test file
        testFilePath = Files.createTempFile("rectangles_test", ".txt");
        List<String> testData = Arrays.asList(
                "rect1 0,0 0,2 3,2 3,0",
                "rect2 1,1 1,3 4,3 4,1",
                "", // Empty line
                "rect3 5,5 5,7 8,7 8,5"
        );
        Files.write(testFilePath, testData);
    }
    
    @Test
    public void testReadLinesFromFile() {
        List<String> lines = reader.readLinesFromFile(testFilePath.toString());
        
        assertEquals(3, lines.size());
        assertEquals("rect1 0,0 0,2 3,2 3,0", lines.get(0));
        assertEquals("rect2 1,1 1,3 4,3 4,1", lines.get(1));
        assertEquals("rect3 5,5 5,7 8,7 8,5", lines.get(2));
    }
    
    @Test
    public void testReadLinesFromNonexistentFile() {
        List<String> lines = reader.readLinesFromFile("nonexistent_file.txt");
        
        assertTrue(lines.isEmpty());
    }
    
    @Test
    public void testReadLinesFromEmptyFile() throws IOException {
        Path emptyFilePath = Files.createTempFile("empty_test", ".txt");
        
        List<String> lines = reader.readLinesFromFile(emptyFilePath.toString());
        
        assertTrue(lines.isEmpty());
        
        // Clean up
        Files.delete(emptyFilePath);
    }
    
    @Test
    public void testFilterEmptyLines() throws IOException {
        Path fileWithEmptyLines = Files.createTempFile("empty_lines_test", ".txt");
        List<String> testData = Arrays.asList(
                "",
                "rect1 0,0 0,2 3,2 3,0",
                "",
                "rect2 1,1 1,3 4,3 4,1",
                "  "  // Line with spaces
        );
        Files.write(fileWithEmptyLines, testData);
        
        List<String> lines = reader.readLinesFromFile(fileWithEmptyLines.toString());
        
        assertEquals(2, lines.size());
        
        // Clean up
        Files.delete(fileWithEmptyLines);
    }
}
