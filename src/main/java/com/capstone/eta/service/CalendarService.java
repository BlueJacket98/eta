package com.capstone.eta.service;
import com.capstone.eta.dao.LockdownAndHolidaysRepository;
import com.capstone.eta.entity.LockdownAndHolidays;
import com.capstone.eta.util.date.DateUtil;
import com.capstone.eta.util.spring.ApplicationContextProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("calendarService")
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
    public Date addNetDaysToDate(String dcCode, Date startDate, Integer numOfDays) {
        List<LockdownAndHolidays> lockdownAndHolidaysInDC = 
                lockdownAndHolidayRepository.findByDcCodeAndStartDateGreaterThanEqualOrderByStartDate(dcCode, new java.sql.Date(startDate.getTime()));
        Date curDate = startDate;
        int idx = 0;
        System.out.println(dcCode);
        System.out.println(startDate);
        System.out.println(numOfDays);
        // System.out.println("Lockdown and holiday: " + lockdownAndHolidaysInDC);
        for (int i = 0; i < lockdownAndHolidaysInDC.size(); i++) {
            System.out.println(lockdownAndHolidaysInDC.get(i).getId());
            System.out.println(lockdownAndHolidaysInDC.get(i).getStartDate());
            System.out.println(lockdownAndHolidaysInDC.get(i).getEndDate());
        }
        while (numOfDays > 0 && idx < lockdownAndHolidaysInDC.size()) {
            // falls within the interval
            if (curDate.compareTo(lockdownAndHolidaysInDC.get(idx).getStartDate()) > 0 
                && curDate.compareTo(lockdownAndHolidaysInDC.get(idx).getEndDate()) < 0) {
                numOfDays -= DateUtil.dateIntervalLengthInDaysAbs(curDate, lockdownAndHolidaysInDC.get(idx).getEndDate());
                curDate = DateUtil.dateAddN(lockdownAndHolidaysInDC.get(idx).getEndDate(), 1);
            // outside the interval
            } else {
                // remain numOfDays < interval size
                if (numOfDays < DateUtil.dateIntervalLengthInDaysAbs(curDate, lockdownAndHolidaysInDC.get(idx).getStartDate())) {
                    curDate = DateUtil.dateAddN(curDate, numOfDays); 
                    return curDate;
                // remain numOfDays >= interval size
                } else {
                    System.out.println("Cur Date: " + curDate.toString());
                    System.out.println("Target Date: " + lockdownAndHolidaysInDC.get(idx).getStartDate().toString());
                    System.out.println(DateUtil.dateIntervalLengthInDaysAbs(curDate, lockdownAndHolidaysInDC.get(idx).getStartDate()));
                    // available date minus, not including holiday start date
                    numOfDays -= DateUtil.dateIntervalLengthInDaysAbs(curDate, lockdownAndHolidaysInDC.get(idx).getStartDate()) - 1;
                    curDate = DateUtil.dateAddN(lockdownAndHolidaysInDC.get(idx).getEndDate(), 1);
                }
            }
            System.out.println(curDate.toString());
            System.out.println(numOfDays);
            idx += 1;
        }
        return curDate;
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
