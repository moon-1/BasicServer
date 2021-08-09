package KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan;

import java.util.List;

public interface floorPlanSearchRepositoryImpl {

    List<String> cityListProvider();

    List<String> floorPlanListProvider(String city);

    String floorPlanJsonFileReader(String city,String fileName);

    void deleteAll();
}
