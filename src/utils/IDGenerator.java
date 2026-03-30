package utils;

import java.util.HashMap;
import java.util.Map;

// Generates unique alphanumeric IDs for new users and library items
public class IDGenerator {
    // A map to track independent counters for different item prefixes
    private static final Map<String, Integer> itemCounters = new HashMap<>();
    private static int userCounter = 1;

    /**
     * Generates a unique ID based on the provided prefix (e.g., BK, MZ, JN).
     */
    public static synchronized String generateItemID(String prefix) {
        // Get current count for prefix, default to 1 if first time.
        int count = itemCounters.getOrDefault(prefix, 1);

        // Save next number for the next time the prefix is used.
        itemCounters.put(prefix, ++count);
        return prefix + '-' + String.format("%03d", count);
    }

    public static synchronized String generateUserID() {
        return "USER_" + String.format("%04d", userCounter++);
    }
}
