package ru.example.helpers;

import java.time.Instant;
import java.util.UUID;

public class GenerationHelper {
    private static long id;
    private static final String prefix = "autotest_";

    synchronized public static long genId() {
        if (id == 0) {
            id = Instant.now().getEpochSecond();
        }
        id++;
        return id;
    }

    synchronized public static String genAppName() {
        return prefix + genId();
    }

    public static String genUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}