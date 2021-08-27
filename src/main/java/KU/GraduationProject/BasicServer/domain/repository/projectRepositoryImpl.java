package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.project.project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface projectRepositoryImpl extends JpaRepository<project,Long> {

    List<project> findByUser_id(@Param(value="id")Long userId);

    List<project> findByFloorPlan_floorPlanId(@Param(value="floorPlanId")Long floorPlanId);
}
