package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.district.area;
import KU.GraduationProject.BasicServer.domain.entity.district.city;
import KU.GraduationProject.BasicServer.domain.repository.areaRepository;
import KU.GraduationProject.BasicServer.domain.repository.cityRepository;
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
public class areaService {

    private static final Logger log = LoggerFactory.getLogger(areaService.class);

    private final areaRepository areaManageRepository;

    private final cityRepository cityManageRepository;

    public List<area> findAll(){
        List<area> area = new ArrayList<>();
        try{
            area = areaManageRepository.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return area;
    }

    public void deleteAll(){
        try{
            areaManageRepository.deleteAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    public List<area> findByCity(Long cityId){
        List<area> areaListByCityId = new ArrayList<>();
        try{
            areaListByCityId = areaManageRepository.findByCity_cityId(cityId);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
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

    public List<area> updateArea(String rootPath){
        try{
            rootPath = rootPath.replace("@","/");
            File[] directories = new File(rootPath).listFiles(File::isDirectory);
            for(File directory : directories){
                if(directory.getName().contains("본부")){
                    String[] fileName = directory.getName().split("본부_");
                    if(cityManageRepository.existsByName(fileName[0]) &&!areaManageRepository.existsByName(fileName[1])){
                        city city = cityManageRepository.findByName(fileName[0]).get();
                        areaManageRepository.save(new area(fileName[1],city));
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
