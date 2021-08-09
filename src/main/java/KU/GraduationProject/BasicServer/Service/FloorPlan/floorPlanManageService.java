package KU.GraduationProject.BasicServer.Service.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.floorPlan;
import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.floorPlanManageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class floorPlanManageService {

    private final floorPlanManageRepositoryImpl floorPlanManageRepository;

    public Long saveFloorPlan(floorPlan floorPlan){
        floorPlanManageRepository.save(floorPlan);
        return floorPlan.getId();
    }

    public List<floorPlan> findAll(){
        return floorPlanManageRepository.findAll();
    }

    public floorPlan findById(Long id){
        floorPlan floorPlan = floorPlanManageRepository.findById(id);
        if(!floorPlanManageRepository.existsById(floorPlan.getId())){
            throw new IllegalArgumentException();
        }
        return floorPlan;
    }

    public floorPlan findByFloorPlanName(String floorPlanName){
        floorPlan floorPlan = floorPlanManageRepository.findByFloorPlanName(floorPlanName);
        if(!floorPlanManageRepository.existsById(floorPlan.getId())){
            throw new IllegalArgumentException();
        }
        return floorPlan;
    }

    public boolean existsById(Long userId){
        if (floorPlanManageRepository.existsById(userId)) {
            return true;
        }
        return false;
    }

    public void deleteAll(){
        floorPlanManageRepository.deleteAll();
    }
}
