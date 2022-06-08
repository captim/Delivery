package com.dumanskyi.delivery.services.impl;

import com.dumanskyi.delivery.entities.AddRequest;
import com.dumanskyi.delivery.entities.db.Request;
import com.dumanskyi.delivery.entities.db.Status;
import com.dumanskyi.delivery.persistence.RequestRepository;
import com.dumanskyi.delivery.services.api.LiqPayService;
import com.dumanskyi.delivery.services.api.RequestService;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final LiqPayService liqPayService;

    public RequestServiceImpl(RequestRepository requestRepository, UserService userService, LiqPayService liqPayService) {
        this.requestRepository = requestRepository;
        this.userService = userService;
        this.liqPayService = liqPayService;
    }

    @Override
    public void createNewRequest(AddRequest input) {
        Request request = Request.builder()
                .creationDate(new Date())
                .deliveryNumber(input.getDeliveryNumber())
                .packageSize(input.getPackageSize())
                .status(Status.NOT_ARRIVED)
                .user(userService.getCurrentUser())
                .transactionId(liqPayService.processRequest(input))
                .build();
        request.setCreationDate(new Date());
        request.setUser(userService.getCurrentUser());
        requestRepository.save(request);
    }

    @Override
    public void updateRequestsStatus(List<Integer> ids) {
        List<Request> requests = requestRepository.findAllById(ids);
        for (Request request: requests) {
            switch (request.getStatus()) {
                case NOT_ARRIVED:
                    request.setStatus(Status.ARRIVED_AT_THE_INTERNATIONAL_WAREHOUSE);
                    break;
                case ARRIVED_AT_THE_INTERNATIONAL_WAREHOUSE:
                    request.setStatus(Status.SENT_TO_UKRAINE);
                    break;
                case SENT_TO_UKRAINE:
                    request.setStatus(Status.ARRIVED_AT_THE_UKRAINIAN_WAREHOUSE);
                    break;
                case ARRIVED_AT_THE_UKRAINIAN_WAREHOUSE:
                    request.setShippingAddress(request.getUser().getShippingAddress());
                    request.setStatus(Status.SENT_TO_CUSTOMER);
                    break;
            }
        }
        requestRepository.flush();
    }

    @Override
    public void finishRequest(Request request) {
        if (!request.getStatus().equals(Status.SENT_TO_CUSTOMER) && !request.getUser().equals(userService.getCurrentUser())) {
            throw new IllegalStateException("Request cannot be finished");
        }
        request.setStatus(Status.DELIVERED);
        requestRepository.flush();
    }

}
