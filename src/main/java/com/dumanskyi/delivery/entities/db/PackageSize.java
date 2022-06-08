package com.dumanskyi.delivery.entities.db;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PackageSize {
    SMALL(0, 1, 3.99f),
    MEDIUM(1, 3, 9.99f),
    LARGE(2, 10, 19.99f);
    private final int id;
    private final int maxWeight;
    private final float price;
    PackageSize(int id, int maxWeight, float price) {
        this.id = id;
        this.maxWeight = maxWeight;
        this.price = price;
    }
    public static PackageSize getById(int id) {
        return Arrays.stream(values())
                .filter(x -> x.getId() == id)
                .findAny()
                .get();
    }
}
