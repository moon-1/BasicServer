package KU.GraduationProject.BasicServer.domain.repository.floorPlan;

import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface floorPlanSearchRepositoryImpl{

    List<String> cityListProvider();

    List<String> floorPlanListProvider(String city);

    String floorPlanJsonFileReader(String city,String fileName);

    void deleteAll();
}
