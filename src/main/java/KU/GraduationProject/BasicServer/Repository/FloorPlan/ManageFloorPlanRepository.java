package KU.GraduationProject.BasicServer.Repository.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.FloorPlan;
import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.ManageFloorPlanRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ManageFloorPlanRepository implements ManageFloorPlanRepositoryImpl {

    private static final Map<Long, FloorPlan> floorPlans = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public boolean existsById(Long id){
        if(floorPlans.containsKey(id)){
            return true;
        }
        else{
            return false;
        }
    }

    public FloorPlan findById(Long id){
        return floorPlans.get(id);
    }

    public FloorPlan findByFloorPlanName(String floorPlanName){
        for (FloorPlan floorPlan : floorPlans.values() ) {
            if(floorPlan.getFloorPlanName().equals(floorPlanName)){
                return floorPlan;
            }
        }
        return null;
    }

    public List<FloorPlan> findAll(){
        return new ArrayList<>(floorPlans.values());
    }

    public FloorPlan save(FloorPlan floorPlan){
        floorPlan.setId(++sequence);
        floorPlans.put(floorPlan.getId(),floorPlan);
        return floorPlan;
    }

}
