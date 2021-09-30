package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.district.city;
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
public class cityService {

    private static final Logger log = LoggerFactory.getLogger(cityService.class);

    private final cityRepository cityRepository;

    public List<city> findAll(){
        List<city> cityList = new ArrayList<>();
        try{
            cityList = cityRepository.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return cityList;
    }

    public Optional<city> findById(Long id){
        Optional<city> city = Optional.empty();
        try{
            city = cityRepository.findById(id);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return city;
    }

    public void deleteAll(){
        cityRepository.deleteAll();
    }

    public List<city> updateCityList(String rootPath){
        try{
            rootPath = rootPath.replace("@","/");
            File[] directories = new File(rootPath).listFiles(File::isDirectory);
            for(File directory : directories){
                if(directory.getName().contains("본부")){
                    String[] fileName = directory.getName().split("_");
                    fileName[0] = fileName[0].replace("본부","");
                    if(!cityRepository.existsByName(fileName[0])){
                        cityRepository.save(new city(fileName[0]));
                    }
                }
            }
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return cityRepository.findAll();
    }

    public Optional<city> findByName(String name){
        Optional<city> city = Optional.empty();
        try{
            city = cityRepository.findByName(name);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return city;
    }

}
