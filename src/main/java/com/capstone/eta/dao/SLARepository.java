package com.capstone.eta.dao;
import java.util.List;

import com.capstone.eta.entity.SLA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("slaRepository")
public interface SLARepository extends JpaRepository<SLA, Integer> {
    List<SLA> findByTaskGroupTypeAndMilestoneNameAndRegionAndDcCodeAndDeploymentSeverity(
        String taskGroupType, String milestoneName, String region, String dcCode, String deploymentSeverity);
}
