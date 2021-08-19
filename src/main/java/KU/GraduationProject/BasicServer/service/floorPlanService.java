package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.area;
import KU.GraduationProject.BasicServer.domain.entity.floorPlan;
import KU.GraduationProject.BasicServer.domain.repository.areaRepositoryImpl;
import KU.GraduationProject.BasicServer.domain.repository.cityRepositoryImpl;
import KU.GraduationProject.BasicServer.domain.repository.floorPlanRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class floorPlanService {

    private static String rootPath = "/Users/moon/Desktop/FloorPlan";

    private static final Logger log = LoggerFactory.getLogger(floorPlanService.class);

    private final floorPlanRepositoryImpl floorPlanRepository;

    private final areaRepositoryImpl areaRepository;

    public List<floorPlan> findAll(){
        List<floorPlan> floorPlan = new ArrayList<floorPlan>();
        try{
            floorPlan = floorPlanRepository.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return floorPlan;
    }

    public void deleteAll(){
        try{
            floorPlanRepository.deleteAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    public List<floorPlan> findByAreaId(Long areaId){
        List<floorPlan> floorPlanListByAreaId = new ArrayList<>();
        try{
            floorPlanListByAreaId = floorPlanRepository.findByArea_areaId(areaId);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return floorPlanListByAreaId;
    }

    public String floorPlanJsonFileReader(String filePath){
        String contents = "";
        try{
            Path path = Paths.get(filePath);
            contents = Files.readString(path);
            contents = base64DataReader(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    private String base64DataReader(String floorPlanJson){
        String[] json = floorPlanJson.split("data:image/png;base64,");
        floorPlanJson = json[1];
        json = floorPlanJson.split("\"}}}");
        floorPlanJson = "data:image/png;base64," + json[0];
        return floorPlanJson;
    }

    public List<floorPlan> updateFloorPlan(){
        try{
            List<area> areaList = areaRepository.findAll();
            for(area area : areaList){
                String city = area.getCity().getName() + "본부";
                File[] files = new File(rootPath + "/" + city + "_" + area.getName()).listFiles(File::isFile);
                for(File file : files){
                    floorPlanRepository.save(new floorPlan(true,
                            floorPlanJsonFileReader(file.getPath()).getBytes(StandardCharsets.UTF_8),
                            area));
                }
            }
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return floorPlanRepository.findAll();
    }
}
