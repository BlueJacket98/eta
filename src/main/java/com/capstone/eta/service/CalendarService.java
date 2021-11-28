package com.capstone.eta.service;
import com.capstone.eta.dao.LockdownAndHolidaysRepository;
import com.capstone.eta.entity.LockdownAndHolidays;
import com.capstone.eta.util.date.DateUtil;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// @RequestMapping(path="/calendar")
public class CalendarService {
    @Autowired
    private LockdownAndHolidaysRepository lockdownAndHolidayRepository;

    /**
     * Get the number of days in lockdown or holidays between two dates in given data center
     * @param DCCode
     * @param date1
     * @param date2
     * @return Integer, number of days
     */
    // @RequestMapping(value="/query")
    // @ResponseBody
    public Integer getLockdownAndHolidaysBetween(String dcCode, Date startDate, Date endDate) {
        List<LockdownAndHolidays> lockdownAndHolidaysInDC = lockdownAndHolidayRepository.findByDcCode(dcCode);
        int totalNumOfDays = 0;
        for (LockdownAndHolidays interval : lockdownAndHolidaysInDC) {
            Date intervalStart = interval.getStartDate();
            Date intervalEnd = interval.getEndDate();
            // if interval start > start and interval end < end
            if (intervalEnd.compareTo(startDate) > 0 || intervalStart.compareTo(endDate) < 0) {
                totalNumOfDays += DateUtil.dateIntervalLengthInDaysAbs(intervalStart, intervalEnd);
            // if interval start < start and interval end < end
            } else if (intervalStart.compareTo(startDate) < 0 && intervalEnd.compareTo(endDate) < 0) {
                totalNumOfDays += DateUtil.dateIntervalLengthInDaysAbs(startDate, intervalEnd);
            // if interval start > start and interval end > end
            } else if (intervalStart.compareTo(startDate) > 0 && intervalEnd.compareTo(endDate) > 0) {
                totalNumOfDays += DateUtil.dateIntervalLengthInDaysAbs(intervalStart, endDate);
            } else {
                ;
            }
        }
        return totalNumOfDays;
    }

    /**
     * TODO
     * @param startDate
     * @param numOfDays
     * @return
     */
    public Date addNetDaysToDate(Date startDate, Integer numOfDays) {
        return new Date(0);
    }

    /**
     * Get all entries in lockdown_and_holidays table
     * Careful to use or not to use at all!
     * @return
     */
    public Iterable<LockdownAndHolidays> getAllLockdownAndHolidays() {
        return lockdownAndHolidayRepository.findAll();
    }

    public static void main(String[] args) {
        // System.out.println(this.getAllUsers());
        
    }
}
