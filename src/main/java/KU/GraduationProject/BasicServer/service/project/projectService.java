package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import KU.GraduationProject.BasicServer.domain.repository.*;
import KU.GraduationProject.BasicServer.dto.createdProjectDto;
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
            createdProjectDto responseDto;
            Date realTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

            //이미 모델링한 이미지일 경우
            if(contourRepository.existsByImageFile_ImageFileId(imageFile.getImageFileId())){
                responseDto = new createdProjectDto(newProjectDto.getName(),realTime,get3DModelDataService.get3DModel(imageFile.getImageFileId()));
            }else{

                //get ImageProcessing
                getImageProcessingDataService.getCoordinate(newProjectDto.getImageFileId());

                //get AIProcessing
                requestAIProcessingDataService.requestWallPlotLength(newProjectDto.getImageFileId());

                responseDto = new createdProjectDto(newProjectDto.getName(),realTime,"start 3D modeling...");
            }

            project project = KU.GraduationProject.BasicServer.domain.entity.project.project.builder()
                    .date(realTime)
                    .name(newProjectDto.getName())
                    .imageFile(imageFile)
                    .user(userInfo.get())
                    .build();

            responseDto.setProjectId(projectRepository.save(project).getProjectId());

            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.CREATED_PROJECT,responseDto), HttpStatus.OK);

        }catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

//    private openProjectDto get3DModel(Long imageFileId){
//        openProjectDto openProjectDto = new openProjectDto();
//        imageFile imageFile = imageFileRepository.getById(imageFileId);
//        openProjectDto.setLengthDto(new lengthDto(imageFile.getHorizontal(),imageFile.getVertical()));
//        openProjectDto.setWall(get3DModelDataService.makeWallDto(imageFileId));
//
//        return openProjectDto;
//    }

    public ResponseEntity<Object> openProject(Long projectId){
        try{
            project project = projectRepository.findById(projectId).get();
            openProjectDto openProjectDto = new openProjectDto();

            openProjectDto.setLengthDto(new lengthDto(10.0,10.0));
            openProjectDto.setWall(get3DModelDataService.makeWallDto(project.getImageFile().getImageFileId()));
            openProjectDto.setFurnitures(get3DModelDataService.makeFurnitureDto(projectId));

            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.OPEN_PROJECT,openProjectDto), HttpStatus.OK);
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
