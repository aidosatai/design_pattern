package lt.esdc.shapes.util;

import java.util.UUID;

public class IDGenerator {
    
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
