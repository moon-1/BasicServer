package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import KU.GraduationProject.BasicServer.domain.repository.*;
import KU.GraduationProject.BasicServer.dto.createdProjectDto;
import KU.GraduationProject.BasicServer.dto.modelDto.isModelExistProjectDto;
import KU.GraduationProject.BasicServer.dto.modelDto.lengthDto;
import KU.GraduationProject.BasicServer.dto.projectDto.*;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.service.dataProcessing.get3DModelDataService;
import KU.GraduationProject.BasicServer.service.dataProcessing.getImageProcessingDataService;
import KU.GraduationProject.BasicServer.service.dataProcessing.requestAIProcessingDataService;
import KU.GraduationProject.BasicServer.util.securityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class projectService {

    @Autowired
    private projectRepository projectRepository;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

    @Autowired
    private get3DModelDataService get3DModelDataService;

    @Autowired
    private contourRepository contourRepository;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private furnitureRepository furnitureRepository;

    @Autowired
    private getImageProcessingDataService getImageProcessingDataService;

    @Autowired
    private requestAIProcessingDataService requestAIProcessingDataService;

    public ResponseEntity<Object> createProject(newProjectDto newProjectDto){
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            imageFile imageFile = imageFileRepository.findById(newProjectDto.getImageFileId()).get();
            if(imageFile==null){ //존재하는 이미지 파일인지 확인
                return new ResponseEntity(defaultResult.res(statusCode.NOT_FOUND, responseMessage.IMAGE_NOT_FOUND, "project name :"+ newProjectDto.getName()),
                        HttpStatus.OK);
            }
            else if(imageFile.getUser() != userInfo.get()){ //사용자가 접근권한이 있는 이미지 파일인지 확인
                return new ResponseEntity(defaultResult.res(statusCode.FORBIDDEN, responseMessage.FORBIDDEN_IMAGE, "Image file id :"+ imageFile.getImageFileId()),
                        HttpStatus.OK);
            }
            isModelExistProjectDto isModelExistProjectDto = new isModelExistProjectDto();
            Date realTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

            //이미 모델링한 이미지일 경우
            if(contourRepository.existsByImageFile_ImageFileId(imageFile.getImageFileId())){
                isModelExistProjectDto.setModelExist(true);
                isModelExistProjectDto.setCreatedProjectDto(new createdProjectDto(newProjectDto.getName(),realTime,get3DModelDataService.get3DModel(imageFile.getImageFileId())));
            }else{
                isModelExistProjectDto.setModelExist(false);
                //get ImageProcessing
                getImageProcessingDataService.getCoordinate(newProjectDto.getImageFileId());

                //get AIProcessing
                requestAIProcessingDataService.requestWallPlotLength(newProjectDto.getImageFileId());
                isModelExistProjectDto.setCreatedProjectDto(new createdProjectDto(newProjectDto.getName(),realTime,"start 3D modeling..."));
            }

            project project = KU.GraduationProject.BasicServer.domain.entity.project.project.builder()
                    .date(realTime)
                    .name(newProjectDto.getName())
                    .imageFile(imageFile)
                    .user(userInfo.get())
                    .build();

            isModelExistProjectDto.getCreatedProjectDto().setProjectId(projectRepository.save(project).getProjectId());

            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.CREATED_PROJECT, isModelExistProjectDto), HttpStatus.OK);

        }catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> openProject(Long projectId){
        try{
            project project = projectRepository.findById(projectId).get();
            projectDto projectDto = new projectDto();
            imageFile imageFile = project.getImageFile();
            projectDto.setLengthDto(new lengthDto(imageFile.getHorizontal(),imageFile.getVertical()));
            projectDto.setWall(get3DModelDataService.makeWallDto(project.getImageFile().getImageFileId()));
            projectDto.setFurnitures(get3DModelDataService.makeFurnitureDto(projectId));

            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.OPEN_PROJECT, projectDto), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> saveProject(saveProjectDto saveProjectDto){
        try{
            List<furniture> furnitureList = furnitureRepository.findAllByProject_ProjectId(saveProjectDto.getProjectId());
            for(furniture furniture : furnitureList){
                furnitureRepository.deleteById(furniture.getFurnitureId());
            }
            project project = projectRepository.findById(saveProjectDto.getProjectId()).get();
            for(furnitureDto furniture : saveProjectDto.getFurnitures()){
//                if(furnitureRepository.existsByProject_ProjectIdAndName(project.getProjectId(),furniture.getName())){
//                    furniture originFurniture = furnitureRepository.findByProject_ProjectIdAndName
//                            (project.getProjectId(),furniture.getName()).get();
//                    originFurniture.setX(furniture.getX());
//                    originFurniture.setY(furniture.getY());
//                    originFurniture.setProject(project);
//                    furnitureRepository.save(originFurniture);
//                }
                {
                    furnitureRepository.save(new furniture(furniture.getName(),furniture.getX(),furniture.getY(),project));
                }
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.SAVE_PROJECT), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }


    }

    public ResponseEntity<Object> showProjectList(){
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            user user = userInfo.get();
            List<project> projects = projectRepository.findAllByUser_UserId(user.getUserId());
            List<projectListDto> projectListDto = new ArrayList<>();
            for(project project : projects){
                projectListDto.add(new projectListDto(project.getProjectId(),
                        project.getName(),
                        project.getDate(),
                        project.getImageFile().getFileDownloadUri(),
                        project.getImageFile().getImageFileId()));
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.SHOW_PROJECT_LIST,projectListDto), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> deleteProject(Long id){
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            if(projectRepository.findById(id).get().getUser() != userInfo.get()) {
                return new ResponseEntity(defaultResult.res(statusCode.FORBIDDEN, responseMessage.FORBIDDEN_PROJECT, "Project id :" + id),
                        HttpStatus.OK);
            }
            projectRepository.deleteById(id);
            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.DELETE_PROJECT, "project ID [" + id + "] is deleted."), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }
}
