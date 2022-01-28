package com.capstone.eta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.entity.LockdownAndHolidays;
import com.capstone.eta.service.CalendarService;
import com.capstone.eta.service.EstimationService;
import com.capstone.eta.util.date.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/eta")
public class ETAController {
    Date curDate;

    @Autowired
    DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    EstimationService estimationService;

    @Autowired
    CalendarService calendarService;

    ETAController() {
        String date = "2021-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            curDate = sdf.parse(date);
        } catch (ParseException except) {
            except.printStackTrace();
        }
    }

    /**
     * Query current date
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/query/cur-date")
    @ResponseBody
    public String getCurDate(HttpServletRequest request, HttpServletResponse response) {
        return curDate.toString();
    }

    /**
     * Set current date
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/request/{date}")
    @ResponseBody
    public String setCurDate(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("date") String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            curDate = sdf.parse(date);
            return "Date set: " + curDate.toString();
        } catch (ParseException except) {
            except.printStackTrace();
            return "Date Parsing Error!";
        }
        
    }

    /**
     * Elapse one day
     * @param request
     * @param response
     */
    @RequestMapping(value = "/request/elapse-one-day")
    @ResponseBody
    public void elapseOneDay(HttpServletRequest request, HttpServletResponse response) {
        curDate = DateUtil.dateAddN(curDate, 1);
    }

    /**
     * Query the ETA of deliveryId, from the stand point of curDate
     * @param request
     * @param response
     * @param deliveryId
     * @return
     */
    @RequestMapping(value = "/query/get-eta/{deliveryId}")
    @ResponseBody
    public String getETA(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable("deliveryId") String deliveryId) {
        Date deliveryStartDate = deliveryInfoRepository.findByDeliveryNumber(deliveryId).get(0).getFpStartDate();
        String dcCode = deliveryInfoRepository.findByDeliveryNumber(deliveryId).get(0).getDcCode();
        estimationService.initEstimationService(deliveryId);
        Integer netEstimatedDuration = estimationService.getEstimatedDuration(curDate, deliveryId);
        Date etaDate = calendarService.addNetDaysToDate(dcCode, deliveryStartDate, netEstimatedDuration);
        return "ETA Date of Delivery No." + deliveryId + ": "+ etaDate.toString() + "\n"
               + "Net Estimated Duration: " + netEstimatedDuration;
    }
}