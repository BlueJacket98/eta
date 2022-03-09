package com.capstone.eta.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "sla_table")
public class SLA {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 50)
    private String id;

    @Column(name = "task_group_type")
    private String taskGroupType;
    
    @Column(name = "milestone_name")
    private String milestoneName;

    @Column(name = "region")
    private String region;

    @Column(name = "dc_code")
    private String dcCode;

    @Column(name = "deployment_severity")
    private String deploymentSeverity;

    @Column(name = "sla")
    private Float sla;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDcCode() {
        return dcCode;
    }

    public void setDcCode(String dcCode) {
        this.dcCode = dcCode;
    }

    public String getDeploymentSeverity() {
        return deploymentSeverity;
    }

    public void setDeploymentSeverity(String deploymentSeverity) {
        this.deploymentSeverity = deploymentSeverity;
    }

    public Float getSla() {
        return sla;
    }

    public void setSla(Float sla) {
        this.sla = sla;
    }


    
    
}
