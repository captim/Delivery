package com.dumanskyi.delivery.services.impl;

import com.dumanskyi.delivery.entities.AddRequest;
import com.dumanskyi.delivery.persistence.RequestRepository;
import com.dumanskyi.delivery.services.api.LiqPayService;
import com.liqpay.LiqPay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LiqPayServiceImpl implements LiqPayService {
    @Value("${liqpay.api.public-key}")
    private String publicKey;
    @Value("${liqpay.api.private-key}")
    private String privateKey;
    @Value("${liqpay.card-receiver}")
    private String receiverCard;
    private final RequestRepository requestRepository;

    public LiqPayServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public String processRequest(AddRequest request) {
        Optional<Integer> maxTransactionIdOptional = requestRepository.findMaxTransactionId();
        String transactionId = maxTransactionIdOptional
                .map(integer -> Integer.toString(integer + 1))
                .orElse("1");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "p2p");
        params.put("version", "3");
        params.put("phone", request.getCreditCard().getPhoneNumber());
        params.put("amount", Float.toString(request.getPackageSize().getPrice()));
        params.put("currency", "USD");
        params.put("description", "Payment for service");
        params.put("order_id", transactionId);
        params.put("receiver_card", receiverCard);
        params.put("card", request.getCreditCard().getNumber());
        params.put("card_exp_month", Integer.toString(request.getCreditCard().getExpMonth()));
        params.put("card_exp_year", Integer.toString(request.getCreditCard().getExpYear()));
        params.put("card_cvv", Integer.toString(request.getCreditCard().getCvv()));
        LiqPay liqpay = new LiqPay(publicKey, privateKey);
        Map<String, Object> res;
        try {
            res = liqpay.api("request", params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactionId;
    }
}
