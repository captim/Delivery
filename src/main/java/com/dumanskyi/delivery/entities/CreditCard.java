package com.dumanskyi.delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Pattern(regexp = "\\d{16}", message = "invalid credit card number")
    private String number;
    @Digits(integer = 2, fraction = 0, message = "should contains 2 digits")
    private int expMonth;
    @Digits(integer = 2, fraction = 0, message = "should contains 2 digits")
    private int expYear;
    @Digits(integer = 3, fraction = 0, message = "should contains 3 digits")
    private int cvv;
    @Pattern(regexp="\\d{10,12}", message = "should contains less then 10 and more then 12")
    private String phoneNumber;
}
