package com.capstone.eta.dao;
import com.capstone.eta.entity.AvgDaysFromStart;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("avgDaysFromStartRepository")
public interface AvgDaysFromStartRepository extends JpaRepository<AvgDaysFromStart, Integer> {
    List<AvgDaysFromStart> findByTaskGroupTypeAndMilestoneNameAndWorkOrderNameAndDeploymentSeverity(
        String taskGroupType, String milestoneName, String workOrderName, String deploymentSeverity);
}
