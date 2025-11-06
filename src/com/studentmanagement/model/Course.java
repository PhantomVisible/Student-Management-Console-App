package com.studentmanagement.model;

public enum Course { // Enum representing the different courses a student can take
    MATHEMATICS,
    PHYSICS,
    SCIENCE_OF_LIFE_AND_EARTH,
    COMPUTER_SCIENCE,
    ENGLISH,
    FRENCH,
    PE; // Physical Education abbreviated

    public static Course fromString(String s) {
        if (s == null) return null;

        // Normalize: trim, replace spaces/dashes with "_", replace "&" with "AND", uppercase
        String normalized = s.trim()
                .replaceAll("[\\s\\-]+", "_")
                .replaceAll("[&]", "AND")
                .toUpperCase();

        // Handle common aliases
        normalized = normalized.replaceAll("SCIENCE_OF_LIFE_AND_EARTH|S_V_E|S_V_EARTH", "SCIENCE_OF_LIFE_AND_EARTH");

        // Handle common aliases
        normalized = normalized.replaceAll("PHYSICAL EDUCATION", "PE");


        try { // Attempt to match the enum
            return Course.valueOf(normalized);
        }
        catch (IllegalArgumentException e) {
            return null; // return null if no matching course
        }
    }
}