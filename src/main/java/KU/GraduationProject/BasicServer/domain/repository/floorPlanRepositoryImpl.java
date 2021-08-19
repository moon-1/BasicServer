package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.floorPlan;
import KU.GraduationProject.BasicServer.domain.entity.project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface floorPlanRepositoryImpl extends JpaRepository<floorPlan,Long> {

    List<floorPlan> findByArea_areaId(@Param(value="areaId")Long areaId);

}
