package lt.esdc.shapes.util;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Tests for {@link IDGenerator} class and different ID generation strategies.
 */
public class IDGeneratorTest {

    @Test
    public void testGenerateId() {
        // Test that generated IDs are not null and are unique
        String id1 = IDGenerator.generateId();
        String id2 = IDGenerator.generateId();
        
        assertNotNull(id1, "Generated ID should not be null");
        assertNotNull(id2, "Generated ID should not be null");
        assertNotEquals(id1, id2, "Generated UUIDs should be unique");
        
        // Standard UUID length is 36 characters
        assertEquals(id1.length(), 36, "UUID length should be 36 characters");
    }
    
    @Test
    public void testGenerateShortId() {
        String shortId1 = IDGenerator.generateShortId();
        String shortId2 = IDGenerator.generateShortId();
        
        assertNotNull(shortId1, "Generated short ID should not be null");
        assertNotNull(shortId2, "Generated short ID should not be null");
        assertNotEquals(shortId1, shortId2, "Generated short IDs should be unique");
        
        // Short ID length is 8 characters
        assertEquals(shortId1.length(), 8, "Short ID length should be 8 characters");
    }
    
    @Test
    public void testGenerateSequentialId() {
        String seqId1 = IDGenerator.generateSequentialId();
        String seqId2 = IDGenerator.generateSequentialId();
        String seqId3 = IDGenerator.generateSequentialId();
        
        assertNotNull(seqId1, "Sequential ID should not be null");
        assertNotNull(seqId2, "Sequential ID should not be null");
        
        // Parse to long to check sequence
        long numId1 = Long.parseLong(seqId1);
        long numId2 = Long.parseLong(seqId2);
        long numId3 = Long.parseLong(seqId3);
        
        assertEquals(numId2, numId1 + 1, "Sequential IDs should increment by 1");
        assertEquals(numId3, numId2 + 1, "Sequential IDs should increment by 1");
    }
    
    @Test
    public void testGenerateTimestampedId() {
        String tsId1 = IDGenerator.generateTimestampedId();
        
        assertNotNull(tsId1, "Timestamped ID should not be null");
        assertTrue(tsId1.contains("-"), "Timestamped ID should contain a hyphen");
        
        String[] parts = tsId1.split("-", 2);
        assertEquals(parts.length, 2, "Timestamped ID should have timestamp and UUID parts");
        
        // First part should be a valid timestamp (long)
        try {
            Long.parseLong(parts[0]);
        } catch (NumberFormatException e) {
            fail("First part of timestamped ID should be a valid timestamp: " + e.getMessage());
        }
        
        // Second part should be exactly 8 characters (shortened UUID)
        assertEquals(parts[1].length(), 8, "UUID part of timestamped ID should be 8 characters");
    }
}
