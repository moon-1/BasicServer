package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class furnitureService {

    private static final Logger log = LoggerFactory.getLogger(furnitureService.class);

    private final KU.GraduationProject.BasicServer.domain.repository.furnitureRepository furnitureRepository;

    public List<furniture> findAll(){
        List<furniture> furnitureList = new ArrayList<>();
        try{
            furnitureList = furnitureRepository.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return furnitureList;
    }

    public Optional<furniture> findById(Long furnitureId){
        Optional<furniture> furniture = Optional.empty();
        try{
            furniture = furnitureRepository.findById(furnitureId);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return furniture;
    }

    public Long save(furniture furniture){
        try{
            furnitureRepository.save(furniture);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return furniture.getFurnitureId();
    }

    private void checkIsFurnitureExist(Long furnitureId){
        if (!furnitureRepository.existsById(furnitureId)) {
            throw new IllegalStateException("존재하지 않는 가구 입니다");
        }
    }


}
