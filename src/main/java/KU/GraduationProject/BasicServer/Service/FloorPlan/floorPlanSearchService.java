package KU.GraduationProject.BasicServer.Service.FloorPlan;

import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.floorPlanSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class floorPlanSearchService {

    private static final Logger log = LoggerFactory.getLogger(floorPlanSearchService.class);
    private final floorPlanSearchRepositoryImpl floorPlanSearchRepository;

    public List<String> cityListProvider(){
        List<String> floorPlan = new ArrayList<String>();
        try{
            floorPlan = floorPlanSearchRepository.cityListProvider();
        }
        catch(Exception ex){
            log.warn(ex.getMessage());
        }
        return floorPlan;
    }

    public List<String> floorPlanListProvider(String city){
        List<String> floorPlan = new ArrayList<String>();
        try{
            floorPlan = floorPlanSearchRepository.floorPlanListProvider(city);
        }
        catch(Exception ex){
            log.warn(ex.getMessage());
        }
        return floorPlan;
    }

    public String floorPlanJsonFileReader(String city,String fileName){
        String content = "";
        try{
            content = floorPlanSearchRepository.floorPlanJsonFileReader(city,fileName);
        }
        catch(Exception ex){
            log.warn(ex.getMessage());
        }
        return content;
    }

    public void deleteAll(){
        try{
            floorPlanSearchRepository.deleteAll();
        }
        catch(Exception ex){
            log.warn(ex.getMessage());
        }
    }
}
