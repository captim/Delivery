package com.dumanskyi.delivery.entities;

import com.dumanskyi.delivery.entities.db.PackageSize;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRequest {
    @NotNull
    @NotBlank
    private String deliveryNumber;
    private PackageSize packageSize;
    @Valid
    private CreditCard creditCard;
}
