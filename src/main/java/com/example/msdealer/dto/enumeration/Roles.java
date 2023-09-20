package com.example.msdealer.dto.enumeration;

import lombok.RequiredArgsConstructor;


public enum Roles {
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String displayName;

    Roles(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return name(); // Return the name of the enum constant (e.g., "ROLE_STOCK_MANAGER") as the role name.
    }
}