package KU.GraduationProject.BasicServer.Service.FloorPlan;

import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.floorPlanSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class floorPlanSearchService {

    private final floorPlanSearchRepositoryImpl searchFloorPlanRepository;

    public List<String> cityListProvider(){
            return searchFloorPlanRepository.cityListProvider();
    }

    public List<String> floorPlanListProvider(String city){
        return searchFloorPlanRepository.floorPlanListProvider(city);
    }

    public String floorPlanJsonFileReader(String city,String fileName){
        return searchFloorPlanRepository.floorPlanJsonFileReader(city,fileName);
    }
}
