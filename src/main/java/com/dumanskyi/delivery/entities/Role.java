package com.dumanskyi.delivery.entities;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER(0),
    ADMIN(1),
    DELIVERY_MAN(2);
    private int id;
    Role(int id) {
        this.id = id;
    }

    Role() {

    }
}
