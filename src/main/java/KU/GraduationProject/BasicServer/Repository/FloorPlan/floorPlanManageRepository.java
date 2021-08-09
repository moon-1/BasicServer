package KU.GraduationProject.BasicServer.Repository.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.floorPlan;
import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.floorPlanManageRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class floorPlanManageRepository implements floorPlanManageRepositoryImpl {

    private static final Map<Long, floorPlan> floorPlans = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public boolean existsById(Long id){
        if(floorPlans.containsKey(id)){
            return true;
        }
        else{
            return false;
        }
    }

    public floorPlan findById(Long id){
        return floorPlans.get(id);
    }

    public floorPlan findByFloorPlanName(String floorPlanName){
        for (KU.GraduationProject.BasicServer.Domain.FloorPlan.floorPlan floorPlan : floorPlans.values() ) {
            if(floorPlan.getFloorPlanName().equals(floorPlanName)){
                return floorPlan;
            }
        }
        return null;
    }

    public List<floorPlan> findAll(){
        return new ArrayList<>(floorPlans.values());
    }

    public floorPlan save(floorPlan floorPlan){
        floorPlan.setId(++sequence);
        floorPlans.put(floorPlan.getId(),floorPlan);
        return floorPlan;
    }

}
