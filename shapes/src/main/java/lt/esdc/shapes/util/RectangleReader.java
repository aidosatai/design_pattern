package lt.esdc.shapes.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for reading lines from a file containing rectangle data.
 */
public class RectangleReader {
    private static final Logger LOGGER = LogManager.getLogger(RectangleReader.class);

    public List<String> readLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(fileName);
        
        if (!Files.exists(path)) {
            LOGGER.error("File does not exist: {}", fileName);
            return lines;
        }
        
        try (Stream<String> fileLines = Files.lines(path)) {
            lines = fileLines
                    .filter(line -> !line.isBlank())
                    .collect(Collectors.toList());
            
            LOGGER.info("Read {} lines from file {}", lines.size(), fileName);
        } catch (IOException e) {
            LOGGER.error("Error reading file: {}", fileName, e);
        }
        
        return lines;
    }
}
