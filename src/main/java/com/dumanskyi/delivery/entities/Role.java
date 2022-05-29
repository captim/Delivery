package com.dumanskyi.delivery.entities;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER(0, "CUSTOMER"),
    ADMIN(1, "ADMIN"),
    DELIVERY_MAN(2, "DELIVERY_MAN");
    private int id;
    private String name;
    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
    Role() {

    }
}
