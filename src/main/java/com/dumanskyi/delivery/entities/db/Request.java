package com.dumanskyi.delivery.entities.db;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status_id")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "package_size_id")
    @Enumerated(EnumType.ORDINAL)
    private PackageSize packageSize;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @NotBlank
    private String deliveryNumber;
    private String transactionId;
    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;
}
