package com.capstone.eta.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.dao.SLARepository;
import com.capstone.eta.dao.WorkOrderRepository;
import com.capstone.eta.entity.DeliveryInfo;
import com.capstone.eta.entity.LockdownAndHolidays;
import com.capstone.eta.entity.SLA;
import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.service.CalendarService;
import com.capstone.eta.service.EstimationService;
import com.capstone.eta.util.date.DateUtil;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/eta")
public class ETAController {
    Date curDate;

    @Autowired
    DeliveryInfoRepository deliveryInfoRepository;
    
    @Autowired
    SLARepository slaRepository;

    @Autowired
    WorkOrderRepository workOrderRepository;

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
    @RequestMapping(value = "/set-date/{date}")
    @ResponseBody
    public String setCurDate(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("date") String date) {
        JsonObject res = new JsonObject();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            curDate = sdf.parse(date);
            res.addProperty("cur_date", curDate.toString());
            return res.toString();
        } catch (ParseException except) {
            res.addProperty("cur_date", "Date Parsing Error");
            return res.toString();
        }
        
    }

    /**
     * Elapse one day
     * @param request
     * @param response
     */
    @RequestMapping(value = "/elapse-one-day")
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
    @RequestMapping(value = "/get-eta/{deliveryId}")
    @ResponseBody
    public String getDeliveryETA(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable("deliveryId") String deliveryId) {
        List<DeliveryInfo> deliveryInfoList = deliveryInfoRepository.findByDeliveryNumber(deliveryId);
        JsonObject res = new JsonObject();
        if (deliveryInfoList.size() == 0) {
            res.addProperty("delivery_id", "Not Found");
            res.addProperty("net_estimated_duration", "Not Found");
            res.addProperty("eta_date", "Not Found");
            return res.toString();
        }
        DeliveryInfo deliveryInfo = deliveryInfoList.get(0);
        String dcCode = deliveryInfo.getDcCode();
        
        List<WorkOrder> startedTasksEntities = workOrderRepository.findByDeliveryNumberAndStartDateLessThanEqual(deliveryId, curDate);
        if (startedTasksEntities.size() > 0 && startedTasksEntities.get(startedTasksEntities.size() - 1).getWorkOrderName().contains(" - Ended")) {
            res.addProperty("delivery_id", deliveryId);
            res.addProperty("net_estimated_duration", "Already completed");
            res.addProperty("eta_date", deliveryInfo.getActualDockDate().toString());
            return res.toString();
        }
        estimationService.initEstimationService(deliveryId, startedTasksEntities);
        Integer netEstimatedDuration = estimationService.getEstimatedDuration(curDate, deliveryId);
        Date etaDate = calendarService.addNetDaysToDate(dcCode, curDate, netEstimatedDuration);
        
        res.addProperty("delivery_id", deliveryId);
        res.addProperty("net_estimated_duration", netEstimatedDuration);
        res.addProperty("eta_date", etaDate.toString());
        
        return res.toString();
    }

    public Date getDeliveryETATest(String deliveryId, Date curDate) {
        List<DeliveryInfo> deliveryInfoList = deliveryInfoRepository.findByDeliveryNumber(deliveryId);
        if (deliveryInfoList.size() == 0) {
            return new Date();
        }
        DeliveryInfo deliveryInfo = deliveryInfoList.get(0);
        String dcCode = deliveryInfo.getDcCode();
        Date deliveryStartDate = deliveryInfo.getFpStartDate();
        List<WorkOrder> startedTasksEntities = workOrderRepository.findByDeliveryNumberAndStartDateLessThanEqual(deliveryId, curDate);
        // if (startedTasksEntities.size() > 0 && startedTasksEntities.get(startedTasksEntities.size() - 1).getWorkOrderName().contains(" - Ended")) {
        //     return deliveryInfo.getActualCtdDate();
        // }
        estimationService.initEstimationService(deliveryId, startedTasksEntities);
        Integer netEstimatedDuration = estimationService.getEstimatedDuration(curDate, deliveryId);
        Date etaDate = calendarService.addNetDaysToDate(dcCode, deliveryStartDate, netEstimatedDuration);
    
        return etaDate;
    }
        
        
    
}