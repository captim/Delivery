package com.dumanskyi.delivery.utils;

import com.dumanskyi.delivery.controller.ResponseMessage;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtil {
    public ResponseEntity<ResponseMessage<?>> noBodyResponseMessage(String message, HttpStatus status) {
        return new ResponseEntity<>(new ResponseMessage<>(message, null), status);
    }
}
