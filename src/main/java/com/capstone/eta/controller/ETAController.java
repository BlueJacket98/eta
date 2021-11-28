package com.capstone.eta.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.entity.LockdownAndHolidays;
import com.capstone.eta.service.CalendarService;
import com.capstone.eta.service.EstimationService;
import com.capstone.eta.util.date.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/eta")
public class ETAController {
    Date curDate;

    DeliveryInfoRepository deliveryInfoRepository;
    EstimationService estimationService;
    CalendarService calendarService;

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
     * Elapse one day
     * @param request
     * @param response
     */
    @RequestMapping(value = "/request/elapse-one-day")
    @ResponseBody
    public void addNDays(HttpServletRequest request, HttpServletResponse response) {
        curDate = DateUtil.dateAddN(curDate, 1);
    }

    /**
     * Query the ETA of deliveryId, from the stand point of curDate
     * @param request
     * @param response
     * @param deliveryId
     * @return
     */
    @RequestMapping(value = "/query/{deliveryId}")
    @ResponseBody
    public String getETA(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable("deliveryId") String deliveryId) {
        Date deliveryStartDate = deliveryInfoRepository.findByDeliveryNumber(deliveryId).get(0).getFpStartDate();
        estimationService.initEstimationService(deliveryId);
        Integer netEstimatedDuration = estimationService.getEstimatedDuration(curDate, deliveryId);
        Date etaDate = calendarService.addNetDaysToDate(deliveryStartDate, netEstimatedDuration);
        return etaDate.toString();
    }


}