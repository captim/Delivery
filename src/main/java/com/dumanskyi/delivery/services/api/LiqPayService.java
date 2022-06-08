package com.dumanskyi.delivery.services.api;

import com.dumanskyi.delivery.entities.AddRequest;

public interface LiqPayService {
    String processRequest(AddRequest request);
}
