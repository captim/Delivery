package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.AddRequest;
import com.dumanskyi.delivery.entities.db.PackageSize;
import com.dumanskyi.delivery.entities.db.ShippingAddress;
import com.dumanskyi.delivery.entities.db.User;
import com.dumanskyi.delivery.persistence.RequestRepository;
import com.dumanskyi.delivery.persistence.ShippingAddressRepository;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.NPService;
import com.dumanskyi.delivery.services.api.RequestService;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class UserController {

    private final UserRepository userRepository;
    private final ShippingAddressRepository addressRepository;
    private final UserService userService;
    private final NPService npService;
    private final RequestService requestService;
    private final RequestRepository requestRepository;

    public UserController(UserRepository userRepository, ShippingAddressRepository addressRepository,
                          UserService userService, NPService npService, RequestService requestService, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.npService = npService;
        this.requestService = requestService;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/cabinet")
    public ModelAndView customerCabinet(HttpServletRequest request) {
        User user = userService.getCurrentUser();
        request.setAttribute("userRepository", userRepository);
        request.setAttribute("npService", npService);
        request.setAttribute("user", user);
        return new ModelAndView("customer_cabinet", "user", user);
    }
    @PostMapping(path = "/shipping-address")
    public String setShippingAddress(HttpServletRequest request, String warehouse_id) {
        System.out.println(request.getContextPath());
        User user = userService.getCurrentUser();
        Optional<ShippingAddress> shippingAddressOptional = addressRepository.findShippingAddressByNpWarehouseId(warehouse_id);
        ShippingAddress shippingAddress;
        if (shippingAddressOptional.isPresent()) {
            shippingAddress = shippingAddressOptional.get();
        } else {
            shippingAddress = new ShippingAddress();
            shippingAddress.setNpWarehouseId(warehouse_id);
            addressRepository.save(shippingAddress);
        }
        user.setShippingAddress(shippingAddress);
        userRepository.flush();
        return "redirect:/customer/cabinet";
    }
    @GetMapping(path = "/request/add")
    public String addRequestPage(HttpServletRequest request, @ModelAttribute("request") AddRequest addRequest) {
        request.setAttribute("userRepository", userRepository);
        request.setAttribute("npService", npService);
        request.setAttribute("user", userService.getCurrentUser());
        return "add_request";
    }
    @PostMapping(path = "/request/add")
    public String addRequest(HttpServletRequest request, @Valid @ModelAttribute("request") AddRequest addRequest, BindingResult bindingResult,
                             @ModelAttribute("packageSizeId") int packageSizeId) {
        request.setAttribute("userRepository", userRepository);
        request.setAttribute("npService", npService);
        request.setAttribute("user", userService.getCurrentUser());
        if (bindingResult.hasErrors() || userService.getCurrentUser().getShippingAddress() == null) {
            return "add_request";
        }
        addRequest.setPackageSize(PackageSize.getById(packageSizeId));
        requestService.createNewRequest(addRequest);
        return "redirect:/customer/cabinet?request_created=true";
    }
    @PostMapping(path = "/request/finished")
    @ResponseBody
    public String finishRequest(@RequestBody int requestId) {
        requestService.finishRequest(requestRepository.getById(requestId));
        return "Cool";
    }
}
