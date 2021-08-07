package KU.GraduationProject.BasicServer.Service.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.FloorPlan;
import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.ManageFloorPlanRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageFloorPlanService {

    private final ManageFloorPlanRepositoryImpl floorPlanRepository;

    public Long saveFloorPlan(FloorPlan floorPlan){
        floorPlanRepository.save(floorPlan);
        return floorPlan.getId();
    }

    public List<FloorPlan> findAll(){
        return floorPlanRepository.findAll();
    }

    public FloorPlan findById(Long id){
        FloorPlan floorPlan = floorPlanRepository.findById(id);
        if(!floorPlanRepository.existsById(floorPlan.getId())){
            throw new IllegalArgumentException();
        }
        return floorPlan;
    }

    public FloorPlan findByFloorPlanName(String floorPlanName){
        FloorPlan floorPlan = floorPlanRepository.findByFloorPlanName(floorPlanName);
        if(!floorPlanRepository.existsById(floorPlan.getId())){
            throw new IllegalArgumentException();
        }
        return floorPlan;
    }

    public boolean existsById(Long userId){
        if (floorPlanRepository.existsById(userId)) {
            return true;
        }
        return false;
    }
}
