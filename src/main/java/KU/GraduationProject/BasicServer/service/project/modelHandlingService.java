package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.furnitureRepository;
import KU.GraduationProject.BasicServer.domain.repository.projectRepository;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.dto.modelDto.model3DDto;
import KU.GraduationProject.BasicServer.dto.projectDto.furnitureDto;
import KU.GraduationProject.BasicServer.dto.modelDto.isModelExistDto;
import KU.GraduationProject.BasicServer.dto.modelDto.wallPlotLengthDto;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.service.dataProcessing.get3DModelDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class modelHandlingService {

    private final projectRepository projectRepository;

    private final furnitureRepository furnitureRepository;

    private final uploadImageFileInfoRepository imageFileRepository;

    private final get3DModelDataService get3DModelDataService;

//    public ResponseEntity<Object> save3DModel(model3DDto model3DDto){
//
//        try{
//            Long projectId = Long.valueOf(model3DDto.getProjectId().longValue());
//            for(furnitureDto furnitureDto : model3DDto.getFurnitures())
//            {
//                furniture furniture;
//                Long id = checkExistFurniture(furnitureDto,projectId);
//                if(!id.equals(0L)){
//                    furniture = furnitureRepository.findById(id).get();
//                    furniture.setX(furnitureDto.getX());
//                    furniture.setY(furnitureDto.getY());
//                }
//                else{
//                    furniture = new furniture();
//                    furniture.setProject(projectRepository.findById(projectId).get());
//                    furniture.setName(furnitureDto.getName());
//                    furniture.setX(furnitureDto.getX());
//                    furniture.setY(furnitureDto.getY());
//                }
//                furnitureRepository.save(furniture);
//            }
//            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.SAVE_MODEL,
//                    model3DDto.getProjectId()), HttpStatus.OK);
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR, responseMessage.INTERNAL_SERVER_ERROR,
//                    ex.getMessage()), HttpStatus.OK);
//        }
//    }

    public void getWallPlotLength(wallPlotLengthDto wallPlotLengthDto){

        try{
            imageFile imageFile = imageFileRepository.findByImageFileId((long) wallPlotLengthDto.getImageFileId()).get();
            imageFile.setHorizontal(wallPlotLengthDto.getHorizontal());
            imageFile.setVertical(wallPlotLengthDto.getVertical());
            imageFileRepository.save(imageFile);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ResponseEntity<Object> checkIs3DModelExist(Long imageFileId){

        try{
            //Image Processing이 전부 완료되었다면
            if(imageFileRepository.findByImageFileId(imageFileId).get().getHorizontal() != 0) {
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.CREATE_3DMODEL_SUCCESS,
                        new isModelExistDto(true,get3DModelDataService.get3DModel(imageFileId))), HttpStatus.OK);
            }
            else{ //Image Processing이 진행중이라면
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.CREATE_3DMODEL_INPROGRESS,
                        new isModelExistDto(false)), HttpStatus.OK);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR, responseMessage.INTERNAL_SERVER_ERROR,
                    ex.getMessage()), HttpStatus.OK);
        }
    }

    private Long checkExistFurniture(furnitureDto furnitureDto,Long projectId){
        List<furniture> furnitureList = furnitureRepository.findAllByProject_ProjectId(projectId);
        for(furniture furniture : furnitureList){
            if(furniture.getName().equals(furnitureDto.getName()))
            {
                return furniture.getFurnitureId();
            }
        }
        return 0L;
    }
}
