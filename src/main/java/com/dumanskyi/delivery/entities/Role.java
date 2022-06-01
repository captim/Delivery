package com.dumanskyi.delivery.entities;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER(0),
    ADMIN(1),
    DELIVERYMAN(2);
    private final int id;
    Role(int id) {
        this.id = id;
    }
}
