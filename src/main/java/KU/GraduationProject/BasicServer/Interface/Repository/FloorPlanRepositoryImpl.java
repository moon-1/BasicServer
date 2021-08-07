package KU.GraduationProject.BasicServer.Interface.Repository;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.FloorPlan;

import java.util.List;

public interface FloorPlanRepositoryImpl {

    boolean existsById(Long id);

    FloorPlan findById(Long id);

    FloorPlan findByFloorPlanName(String floorPlanName);

    List<FloorPlan> findAll();

    FloorPlan save(FloorPlan floorPlan);

}
