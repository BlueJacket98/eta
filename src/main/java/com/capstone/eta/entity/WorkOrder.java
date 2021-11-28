package com.capstone.eta.entity;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "work_order")
public class WorkOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "delivery_number")
    private String deliveryNumber;

    @Column(name = "work_order_name")
    private String workOrderName;

    @Column(name = "milestone_name")
    private String milestoneName;

    @Column(name = "work_order_status_name")
    private String workOrderStatusName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "late_start_date")
    private Date lateStartDate;

    @Column(name = "late_end_date")
    private Date lateEndDate;

    @Column(name = "task_group_type")
    private String taskGroupType;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "work_order_assigned_to_team")
    private String workOrderAssignedToTeam;
    
    @Column(name = "work_order_modified_date")
    private String workOrderModifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getWorkOrderStatusName() {
        return workOrderStatusName;
    }

    public void setWorkOrderStatusName(String workOrderStatusName) {
        this.workOrderStatusName = workOrderStatusName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLateStartDate() {
        return lateStartDate;
    }

    public void setLateStartDate(Date lateStartDate) {
        this.lateStartDate = lateStartDate;
    }

    public Date getLateEndDate() {
        return lateEndDate;
    }

    public void setLateEndDate(Date lateEndDate) {
        this.lateEndDate = lateEndDate;
    }

    public String getTaskGroupType() {
        return taskGroupType;
    }

    public void setTaskGroupType(String taskGroupType) {
        this.taskGroupType = taskGroupType;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getWorkOrderAssignedToTeam() {
        return workOrderAssignedToTeam;
    }

    public void setWorkOrderAssignedToTeam(String workOrderAssignedToTeam) {
        this.workOrderAssignedToTeam = workOrderAssignedToTeam;
    }

    public String getWorkOrderModifiedDate() {
        return workOrderModifiedDate;
    }

    public void setWorkOrderModifiedDate(String workOrderModifiedDate) {
        this.workOrderModifiedDate = workOrderModifiedDate;
    }


    

}
