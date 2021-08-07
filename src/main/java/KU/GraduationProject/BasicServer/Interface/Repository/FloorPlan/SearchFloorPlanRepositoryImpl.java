package KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.FloorPlan;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SearchFloorPlanRepositoryImpl {

    Map<String, List<String>> cityListProvider();

    Map<String, List<String>> floorPlanListProvider(String city);

//    Optional<String> findByLocal(String local);
//
//    Optional<String> findByArea(String area);
//
//    FloorPlan findByFloorPlanName(String floorPlanName);
//
//    InputStream findFloorPlanImage(String imagePath);
}
