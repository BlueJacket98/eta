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
@RequestMapping(path="/test")
public class EvaluationController {
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

    @Autowired
    ETAController etaController;

    EvaluationController() {
        ;
    }

    @RequestMapping(value = "/evaluate")
    @ResponseBody
    public void getDeliveryETA(HttpServletRequest request, HttpServletResponse response) {
        Date startDateLower = DateUtil.getDateFromString("2021-01-01");
        Date startDateUpper = DateUtil.getDateFromString("2021-05-01");
        System.out.println(startDateLower);
        System.out.println(startDateUpper);
        List<DeliveryInfo> deliveries = deliveryInfoRepository.findByFpStartDateBetween(
            startDateLower, startDateUpper
        );
        for (DeliveryInfo delivery : deliveries) {
            String deliveryId = delivery.getDeliveryNumber();
            Date fpStartDate = delivery.getFpStartDate();
            Date actualCtdDate = delivery.getActualCtdDate();
            List<Date> allDates = DateUtil.getAllDatesBetween(fpStartDate, actualCtdDate);
            System.out.println("-----------------------------------------------------------------------------");
            System.out.printf("%10s %10s %10s %10s %5s", "Deliv. Id", "Cur Date", "Est. CTD", "Act. CTD", "Diff");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");
            for (Date curDate : allDates) {
                JsonObject res = etaController.getDeliveryETATest(deliveryId, curDate);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                Date estimatedCtdDate;
                try {
                    estimatedCtdDate = sdf.parse(res.get("eta_date").getAsString());
                } catch (ParseException e) {
                    estimatedCtdDate = new Date();
                }
                System.out.println(deliveryId);
                System.out.println(curDate.toString());
                System.out.println(estimatedCtdDate.toString());
                System.out.println(actualCtdDate.toString());
                System.out.println(DateUtil.dateDiffInDaysAbs(estimatedCtdDate, actualCtdDate).toString());
                // int diff = DateUtil.dateDiffInDaysAbs(estimatedCtdDate, actualCtdDate).intValue();
                // System.out.format("%10s %10s %10s %10s %5d", 
                //                     deliveryId, 
                //                     curDate.toString(), 
                //                     estimatedCtdDate.toString(), 
                //                     actualCtdDate.toString(),
                //                     diff);
                System.out.println();
            }
            
        }

    }
}
