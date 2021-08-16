package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.city;
import KU.GraduationProject.BasicServer.domain.entity.user;
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
public class cityManageService {

    private static final Logger log = LoggerFactory.getLogger(cityManageService.class);

    //private static String rootPath = "/home/ec2-user/FloorPlan/";
    private static String rootPath = "/Users/moon/Desktop/FloorPlan";

    private final cityManageRepositoryImpl cityManageRepositoryImpl;

    public List<city> findAll(){
        List<city> cityList = new ArrayList<>();
        try{
            cityList = cityManageRepositoryImpl.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return cityList;
    }

    public Optional<city> findById(Long id){
        Optional<city> city = Optional.empty();
        try{
            city = cityManageRepositoryImpl.findById(id);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return city;
    }

    public void deleteAll(){
        cityManageRepositoryImpl.deleteAll();
    }

    public List<city> updateCityList(){
        try{
            File[] directories = new File(rootPath).listFiles(File::isDirectory);
            for(File directory : directories){
                if(directory.getName().contains("본부")){
                    String[] fileName = directory.getName().split("_");
                    fileName[0] = fileName[0].replace("본부","");
                    if(!cityManageRepositoryImpl.existsByName(fileName[0])){
                        cityManageRepositoryImpl.save(new city(fileName[0]));
                    }
                }
            }
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return cityManageRepositoryImpl.findAll();
    }

    public Optional<city> findByName(String name){
        Optional<city> city = Optional.empty();
        try{
            city = cityManageRepositoryImpl.findByName(name);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return city;
    }

}
