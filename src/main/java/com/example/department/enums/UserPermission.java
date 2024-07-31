package com.example.department.enums;

public enum UserPermission {
    ADMIN_PERMISSION("admin_permission"),
    MANAGER_PERMISSION("manager_permission");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
