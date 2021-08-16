package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.area;
import KU.GraduationProject.BasicServer.domain.entity.city;
import KU.GraduationProject.BasicServer.domain.repository.areaManageRepositoryImpl;
import KU.GraduationProject.BasicServer.domain.repository.cityManageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class areaManageService {

    private static final Logger log = LoggerFactory.getLogger(areaManageService.class);

    private static String rootPath = "/Users/moon/Desktop/FloorPlan";

    private final areaManageRepositoryImpl areaManageRepository;

    private final cityManageRepositoryImpl cityManageRepository;

    public List<area> findAll(){
        return areaManageRepository.findAll();
    }

    public List<area> findByCity(Long cityId){
        List<area> areaList = areaManageRepository.findAll();
        List<area> areaListByCityId = new ArrayList<>();
        for(area area : areaList){
            if(area.getCityId().equals(cityId)){
                areaListByCityId.add(area);
            }
        }
        return areaListByCityId;
    }

    public Optional<area> findByName(String name){
        Optional<area> area = Optional.empty();
        try{
            area = areaManageRepository.findByName(name);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return area;
    }

    public List<area> updateArea(){
        try{
            File[] directories = new File(rootPath).listFiles(File::isDirectory);
            for(File directory : directories){
                if(directory.getName().contains("본부")){
                    String[] fileName = directory.getName().split("_");
                    fileName[0] = fileName[0].replace("본부","");
                    if(cityManageRepository.existsByName(fileName[0])){
                        areaManageRepository.save(new
                                area(fileName[1],(cityManageRepository.findByName(fileName[0])).get().getCityId()));
                    }
                }
            }
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return areaManageRepository.findAll();
    }

}
