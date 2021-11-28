package com.capstone.eta.entity;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "delivery_info")
public class DeliveryInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mdmid")
    private String mdmid;

    @Column(name = "delivery_number")
    private String deliveryNumber;

    @Column(name = "region")
    private String region;

    @Column(name = "dc_code")
    private String dcCode;

    @Column(name = "resoure_type")
    private String resoureType;
    
    @Column(name = "intent")
    private String intent;

    @Column(name = "is_mainstream")
    private String isMainstream;
    
    @Column(name = "fp_lock_to_dock_base_sla")
    private Integer fpLockToDockBaseSla;

    @Column(name = "fp_lock_to_dock_total_sla")
    private Integer fpLockToDockTotalSla;

    @Column(name = "fp_start_date")
    private Date fpStartDate;

    @Column(name = "fp_lock_date")
    private Date fpLockDate;

    @Column(name = "eta_ctd_date")
    private Date etaCtdDate;

    @Column(name = "actual_ctd_date")
    private Date actualCtdDate;

    @Column(name = "eta_dock_date")
    private Date etaDockDate;

    @Column(name = "actual_dock_date")
    private Date actualDockDate;

    @Column(name = "deployment_severity")
    private String deploymentSeverity;

    @Column(name = "deployment_path")
    private String deploymentPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMdmid() {
        return mdmid;
    }

    public void setMdmid(String mdmid) {
        this.mdmid = mdmid;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
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

    public String getResoureType() {
        return resoureType;
    }

    public void setResoureType(String resoureType) {
        this.resoureType = resoureType;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getIsMainstream() {
        return isMainstream;
    }

    public void setIsMainstream(String isMainstream) {
        this.isMainstream = isMainstream;
    }

    public Integer getFpLockToDockBaseSla() {
        return fpLockToDockBaseSla;
    }

    public void setFpLockToDockBaseSla(Integer fpLockToDockBaseSla) {
        this.fpLockToDockBaseSla = fpLockToDockBaseSla;
    }

    public Integer getFpLockToDockTotalSla() {
        return fpLockToDockTotalSla;
    }

    public void setFpLockToDockTotalSla(Integer fpLockToDockTotalSla) {
        this.fpLockToDockTotalSla = fpLockToDockTotalSla;
    }

    public Date getFpStartDate() {
        return fpStartDate;
    }

    public void setFpStartDate(Date fpStartDate) {
        this.fpStartDate = fpStartDate;
    }

    public Date getFpLockDate() {
        return fpLockDate;
    }

    public void setFpLockDate(Date fpLockDate) {
        this.fpLockDate = fpLockDate;
    }

    public Date getEtaCtdDate() {
        return etaCtdDate;
    }

    public void setEtaCtdDate(Date etaCtdDate) {
        this.etaCtdDate = etaCtdDate;
    }

    public Date getActualCtdDate() {
        return actualCtdDate;
    }

    public void setActualCtdDate(Date actualCtdDate) {
        this.actualCtdDate = actualCtdDate;
    }

    public Date getEtaDockDate() {
        return etaDockDate;
    }

    public void setEtaDockDate(Date etaDockDate) {
        this.etaDockDate = etaDockDate;
    }

    public Date getActualDockDate() {
        return actualDockDate;
    }

    public void setActualDockDate(Date actualDockDate) {
        this.actualDockDate = actualDockDate;
    }

    public String getDeploymentSeverity() {
        return deploymentSeverity;
    }

    public void setDeploymentSeverity(String deploymentSeverity) {
        this.deploymentSeverity = deploymentSeverity;
    }

    public String getDeploymentPath() {
        return deploymentPath;
    }

    public void setDeploymentPath(String deploymentPath) {
        this.deploymentPath = deploymentPath;
    }

    
}
