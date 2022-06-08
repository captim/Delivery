package com.dumanskyi.delivery.entities.db;

import lombok.Getter;

@Getter
public enum Status {
    NOT_ARRIVED(0, "Not arrived", "Package is not arrived at the international warehouse yet. We are waiting for it."),
    ARRIVED_AT_THE_INTERNATIONAL_WAREHOUSE(1, "Arrived at the international warehouse", "Package was arrived at the international warehouse. We are going to send it to Ukraine."),
    SENT_TO_UKRAINE(2, "Sent to Ukraine", "Package was sent to Ukrainian warehouse."),
    ARRIVED_AT_THE_UKRAINIAN_WAREHOUSE(3,"Arrived at the Ukrainian warehouse", "Package was arrived at the Ukrainian warehouse. We are going to send it to the customer."),
    SENT_TO_CUSTOMER(4, "Sent to the customer", "Package was sent to the customer."),
    DELIVERED(5, "Delivered", "Package was delivered to the customer");
    private final int id;
    private final String shortMessage;
    private final String message;
    Status(int id, String shortMessage, String message) {
        this.id = id;
        this.shortMessage = shortMessage;
        this.message = message;
    }

}
