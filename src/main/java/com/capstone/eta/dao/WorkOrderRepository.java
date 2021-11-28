package com.capstone.eta.dao;
import java.util.*;

import com.capstone.eta.entity.DeliveryInfo;
import com.capstone.eta.entity.WorkOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Integer> {
    List<WorkOrder> findByDeliveryNumberAndStartDateLessThanEqual(String deliveryNumber, Date curDate);
}
