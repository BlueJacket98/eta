package com.capstone.eta.dao;
import com.capstone.eta.entity.LockdownAndHolidays;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockdownAndHolidaysRepository extends JpaRepository<LockdownAndHolidays, Integer> {
    List<LockdownAndHolidays> findByType(String type);
    List<LockdownAndHolidays> findByStartDate(Date startDate);
    List<LockdownAndHolidays> findByEndDate(Date endDate);
    List<LockdownAndHolidays> findByName(String name);
    List<LockdownAndHolidays> findByDcCode(String dcCode);
}
