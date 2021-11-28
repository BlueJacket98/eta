package com.capstone.eta.dao;
import com.capstone.eta.entity.DeliveryInfo;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * master data
 */
@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Integer> {
    List<DeliveryInfo> findByDeliveryNumber(String deliveryNumber);
    List<DeliveryInfo> findByDeliveryNumberAndFpStartDateLessThanEqual(String deliveryNumber, Date fpStartDate);
}
