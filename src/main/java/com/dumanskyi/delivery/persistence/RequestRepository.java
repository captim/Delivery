package com.dumanskyi.delivery.persistence;

import com.dumanskyi.delivery.entities.db.Request;
import com.dumanskyi.delivery.entities.db.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "SELECT MAX(r.transaction_id) FROM requests r", nativeQuery = true)
    Optional<Integer> findMaxTransactionId();
    List<Request> findRequestByStatus(Status status);
}
