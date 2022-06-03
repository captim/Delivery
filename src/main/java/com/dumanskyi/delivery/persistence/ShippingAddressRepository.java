package com.dumanskyi.delivery.persistence;

import com.dumanskyi.delivery.entities.db.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {
    Optional<ShippingAddress> findShippingAddressByNpWarehouseId(String npWarehouseId);
}
