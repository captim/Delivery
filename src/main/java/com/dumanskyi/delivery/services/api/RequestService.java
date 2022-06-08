package com.dumanskyi.delivery.services.api;

import com.dumanskyi.delivery.entities.AddRequest;
import com.dumanskyi.delivery.entities.db.Request;

import java.util.List;

public interface RequestService {
    void createNewRequest(AddRequest input);
    void updateRequestsStatus(List<Integer> ids);
    void finishRequest(Request request);
}
