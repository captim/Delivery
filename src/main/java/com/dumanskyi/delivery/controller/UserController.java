package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.db.ShippingAddress;
import com.dumanskyi.delivery.entities.db.User;
import com.dumanskyi.delivery.persistence.ShippingAddressRepository;
import com.dumanskyi.delivery.persistence.UserRepository;
import com.dumanskyi.delivery.services.api.NPService;
import com.dumanskyi.delivery.services.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class UserController {

    private final UserRepository userRepository;
    private final ShippingAddressRepository addressRepository;
    private final UserService userService;
    private final NPService npService;

    public UserController(UserRepository userRepository, ShippingAddressRepository addressRepository, UserService userService, NPService npService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.npService = npService;
    }

    @GetMapping("/cabinet")
    public ModelAndView customerCabinet(HttpServletRequest request) {
        String username = userService.getCurrentUser().getUsername();
        User user = userRepository.findByUsername(username);
        request.setAttribute("userRepository", userRepository);
        request.setAttribute("npService", npService);
        request.setAttribute("user", user);
        return new ModelAndView("customer_cabinet", "user", user);
    }
    @PostMapping(path = "/shipping-address")
    public String setShippingAddress(String warehouse_id) {
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
}
