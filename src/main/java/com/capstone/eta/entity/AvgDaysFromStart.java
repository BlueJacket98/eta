package com.capstone.eta.entity;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "avg_days_from_start")
public class AvgDaysFromStart {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 50)
    private String id;

    @Column(name = "task_group_type")
    private String taskGroupType;

    @Column(name = "milestone_name")
    private String milestoneName;

    @Column(name = "work_order_name")
    private String workOrderName;

    @Column(name = "deployment_severity")
    private String deploymentSeverity;

    @Column(name = "avg_days")
    private Float avgDays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskGroupType() {
        return taskGroupType;
    }

    public void setTaskGroupType(String taskGroupType) {
        this.taskGroupType = taskGroupType;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getDeploymentSeverity() {
        return deploymentSeverity;
    }

    public void setDeploymentSeverity(String deploymentSeverity) {
        this.deploymentSeverity = deploymentSeverity;
    }

    public Float getAvgDays() {
        return avgDays;
    }

    public void setAvgDays(Float avgDays) {
        this.avgDays = avgDays;
    }

    


    
}
