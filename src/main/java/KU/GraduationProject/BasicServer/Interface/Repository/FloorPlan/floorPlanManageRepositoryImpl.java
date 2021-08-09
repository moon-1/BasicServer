package KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.floorPlan;

import java.util.List;

public interface floorPlanManageRepositoryImpl {

    boolean existsById(Long id);

    floorPlan findById(Long id);

    floorPlan findByFloorPlanName(String floorPlanName);

    List<floorPlan> findAll();

    floorPlan save(floorPlan floorPlan);

}
