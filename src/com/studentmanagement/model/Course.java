package com.studentmanagement.model;

public enum Course {
    MATHEMATICS,
    PHYSICS,
    SCIENCE_OF_LIFE_AND_EARTH,
    COMPUTER_SCIENCE,
    ENGLISH,
    FRENCH,
    PE;

    public static Course fromString(String s) {
        if (s == null) return null;
        String normalized = s.trim().replaceAll("[\\s\\-]+", "_").replaceAll("[&]", "AND").toUpperCase();
        normalized = normalized.replaceAll("SCIENCE_OF_LIFE_AND_EARTH|S_V_E|S_V_EARTH", "SCIENCE_OF_LIFE_AND_EARTH");
        normalized = normalized.replaceAll("PHYSICAL EDUCATION", "PE");
        try { return Course.valueOf(normalized); } 
        catch (IllegalArgumentException e) { return null; }
    }
}