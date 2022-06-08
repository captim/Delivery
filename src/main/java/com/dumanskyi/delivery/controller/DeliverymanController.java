package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.persistence.RequestRepository;
import com.dumanskyi.delivery.services.api.NPService;
import com.dumanskyi.delivery.services.api.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/deliveryman")
public class DeliverymanController {
    private final RequestRepository requestRepository;
    private final NPService npService;
    private final RequestService requestService;

    public DeliverymanController(RequestRepository requestRepository, NPService npService, RequestService requestService) {
        this.requestRepository = requestRepository;
        this.npService = npService;
        this.requestService = requestService;
    }

    @GetMapping("/package_receiving")
    public String packageReceivingPage(HttpServletRequest request) {
        request.setAttribute("requestRepository", requestRepository);
        return "package_receiving";
    }
    @GetMapping("/package_sending")
    public String packageSendingPage(HttpServletRequest request) {
        request.setAttribute("npService", npService);
        request.setAttribute("requestRepository", requestRepository);
        return "package_sending";
    }
    @PostMapping(value = "/updateRequestStatus")
    @ResponseBody
    public String packageReceiving(HttpServletRequest request, @RequestBody List<Integer> requestIds) {
        requestService.updateRequestsStatus(requestIds);
        return "Cool";
    }
}
