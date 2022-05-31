package com.dumanskyi.delivery.entities.validators.annotations;

public enum DatabaseField {
    EMAIL("email"),
    USERNAME("username");
    final String value;
    DatabaseField(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
