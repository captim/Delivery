package com.dumanskyi.delivery.controller;

import com.dumanskyi.delivery.entities.db.User;
import com.dumanskyi.delivery.entities.novaposhta.responses.KeyValue;
import com.dumanskyi.delivery.services.api.NPService;
import com.dumanskyi.delivery.services.api.UserService;
import com.liqpay.LiqPay;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final UserService userService;
    private final NPService NPService;
    @Value("${liqpay.api.public-key}")
    private String publicKey;
    @Value("${liqpay.api.private-key}")
    private String privateKey;

    public MainController(UserService userService, NPService NPService) {
        this.userService = userService;
        this.NPService = NPService;
    }

    @GetMapping(path = "/welcome")
    public String welcomePage() {
        return "index";
    }

    @GetMapping(path = "/")
    public ModelAndView indexPage() {
        return new ModelAndView("redirect:/welcome");
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(path = "/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String RegisterNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.createCustomer(user);
        return "redirect:/login?customerUserCreated=true";
    }
    @GetMapping(path = "/post", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String post() {
        return NPService.getStandardWarehouseTypeId();
    }
    @GetMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<KeyValue> cities(@RequestParam(required = false) String name, @RequestParam int amount) {
        return NPService.getCitiesIdByName(name, amount);
    }
    @GetMapping(path = "/warehouses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<KeyValue> warehouses(@RequestParam String city, int number) {
        return NPService.getWarehouseByCityIdAndNumber(city, number);
    }
    @GetMapping(path = "/test")
    @ResponseBody
    public String test() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "p2p");
        params.put("version", "3");
        params.put("phone", "380503079905");
        params.put("amount", "1");
        params.put("currency", "USD");
        params.put("description", "Payment for service");
        params.put("order_id", "4");
        params.put("receiver_card", "5168757371045958");
        params.put("card", "5354322072921397");
        params.put("card_exp_month", "10");
        params.put("card_exp_year", "23");
        params.put("card_cvv", "950");
        LiqPay liqpay = new LiqPay(publicKey, privateKey);
        Map<String, Object> res = liqpay.api("request", params);
        System.out.println(res.get("status"));
        return res.toString();
    }
}
