package com.innowise.core.entity;

public enum UserRole {

    SYS_ADMIN("SYS_ADMIN"),
    ADMIN("ADMIN"),
    DISPATCHER("DISPATCHER"),
    MANAGER("MANAGER"),
    DRIVER("DRIVER"),
    COMPANY_OWNER("COMPANY_OWNER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
