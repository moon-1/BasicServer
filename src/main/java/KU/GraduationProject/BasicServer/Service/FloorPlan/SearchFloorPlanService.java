package KU.GraduationProject.BasicServer.Service.FloorPlan;

import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.SearchFloorPlanRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchFloorPlanService {

    private final SearchFloorPlanRepositoryImpl searchFloorPlanRepository;

    public Map<String, List<String>> cityListProvider(){
            return searchFloorPlanRepository.cityListProvider();
    }

    public Map<String, List<String>> floorPlanListProvider(String area){
        return searchFloorPlanRepository.floorPlanListProvider(area);
    }

}
