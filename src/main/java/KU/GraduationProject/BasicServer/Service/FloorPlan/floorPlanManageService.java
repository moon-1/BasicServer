package KU.GraduationProject.BasicServer.Service.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.floorPlan;
import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.floorPlanManageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class floorPlanManageService {

    private final floorPlanManageRepositoryImpl floorPlanRepository;

    public Long saveFloorPlan(floorPlan floorPlan){
        floorPlanRepository.save(floorPlan);
        return floorPlan.getId();
    }

    public List<floorPlan> findAll(){
        return floorPlanRepository.findAll();
    }

    public floorPlan findById(Long id){
        floorPlan floorPlan = floorPlanRepository.findById(id);
        if(!floorPlanRepository.existsById(floorPlan.getId())){
            throw new IllegalArgumentException();
        }
        return floorPlan;
    }

    public floorPlan findByFloorPlanName(String floorPlanName){
        floorPlan floorPlan = floorPlanRepository.findByFloorPlanName(floorPlanName);
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
