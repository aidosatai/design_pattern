package lt.esdc.shapes.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique identifiers.
 * Provides various ID generation strategies including UUID and sequential IDs.
 */
public class IDGenerator {
    
    /**
     * Thread-safe counter for sequential IDs
     */
    private static final AtomicLong counter = new AtomicLong(0);
    
    /**
     * Generates a universally unique identifier (UUID)
     * This is the default and recommended method for most cases.
     * 
     * @return A UUID string representation
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Generates a shortened UUID (first 8 characters)
     * Useful when a shorter ID is needed but uniqueness is still important.
     * 
     * @return A shortened UUID string
     */
    public static String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    
    /**
     * Generates a sequential ID.
     * Useful when the order of creation is important or for testing.
     * 
     * @return A sequential ID string
     */
    public static String generateSequentialId() {
        return String.valueOf(counter.incrementAndGet());
    }
    
    /**
     * Generates a timestamped ID with UUID suffix.
     * Useful when both creation time and uniqueness are important.
     * 
     * @return A timestamped UUID string
     */
    public static String generateTimestampedId() {
        return System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
