package KU.GraduationProject.BasicServer.Repository.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.FloorPlan;
import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.SearchFloorPlanRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class SearchFloorPlanRepository implements SearchFloorPlanRepositoryImpl {

    private static String rootPath = "/Users/moon/Desktop/FloorPlan";

    private static final Map<String,Optional<String>> floorPlansPath = new ConcurrentHashMap<>();

    public Map<String,List<String>> cityListProvider(){
        Map<String,List<String>> path = new ConcurrentHashMap<>();
        List<String> directoryList = new ArrayList<>();
        File[] directories = new File(rootPath).listFiles(File::isDirectory);
        for(File directory : directories){
            directoryList.add(directory.getName());
        }
        path.put(rootPath,directoryList);
        return path;
    }

    public Map<String,List<String>> floorPlanListProvider(String area){
        Map<String,List<String>> floorPlanPath = new ConcurrentHashMap<>();
        List<String> floorPlanList = new ArrayList<>();
        File[] files = new File(rootPath+"/"+area).listFiles(File::isFile);
        for(File file : files){
            floorPlanList.add(file.getName());
        }
        floorPlanPath.put(rootPath+"/"+area,floorPlanList);
        return floorPlanPath;
    }

}
