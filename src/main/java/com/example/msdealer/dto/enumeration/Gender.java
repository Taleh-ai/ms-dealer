package com.example.msdealer.dto.enumeration;

public enum Gender {
    MALE("Male", "M"),
    FEMALE("Female", "F");
    private final String displayName;
    private final String abbreviation;

    Gender(String displayName, String abbreviation) {
        this.displayName = displayName;
        this.abbreviation = abbreviation;
    }
}
