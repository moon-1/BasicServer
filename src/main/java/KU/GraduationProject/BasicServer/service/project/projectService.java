package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.contour;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.point;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.pointType;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall;
import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import KU.GraduationProject.BasicServer.domain.repository.*;
import KU.GraduationProject.BasicServer.dto.AIProcessingDto.contourLengthDto;
import KU.GraduationProject.BasicServer.dto.createdProjectDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.pointDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.wallDto;
import KU.GraduationProject.BasicServer.dto.projectDto.*;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
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
    private contourRepository contourRepository;

    @Autowired
    private wallRepository wallRepository;

    @Autowired
    private pointRepository pointRepository;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private furnitureRepository furnitureRepository;

    public ResponseEntity<Object> createProject(newProjectDto newProjectDto){
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            imageFile imageFile = imageFileRepository.findById(newProjectDto.getImageFileId()).get();
            if(imageFile==null){
                return new ResponseEntity(defaultResult.res(statusCode.NOT_FOUND, responseMessage.IMAGE_NOT_FOUND, "project name :"+ newProjectDto.getName()),
                        HttpStatus.OK);
            }
            else if(imageFile.getUser() != userInfo.get()){
                return new ResponseEntity(defaultResult.res(statusCode.FORBIDDEN, responseMessage.FORBIDDEN_IMAGE, "Image file id :"+ imageFile.getImageFileId()),
                        HttpStatus.OK);
            }
            project project = KU.GraduationProject.BasicServer.domain.entity.project.project.builder()
                    .date(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .name(newProjectDto.getName())
                    .imageFile(imageFile)
                    .user(userInfo.get())
                    .build();

            projectRepository.save(project);

            createdProjectDto responseDto = new createdProjectDto(project.getName(),project.getDate());

            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.CREATED_PROJECT,responseDto), HttpStatus.OK);

        }catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> openProject(Long projectId){
        try{
            project project = projectRepository.findById(projectId).get();
            openProjectDto openProjectDto = new openProjectDto();

            openProjectDto.setLengthDto(new lengthDto(10.0,10.0));
            openProjectDto.setWall(makeWallDto(project.getImageFile().getImageFileId()));
            openProjectDto.setFurnitures(makeFurnitureDto(projectId));

            return new ResponseEntity(defaultResult.res(statusCode.OK,
                    responseMessage.OPEN_PROJECT,openProjectDto), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    private ArrayList<wallDto> makeWallDto(Long imageFileId){

        ArrayList<wallDto> wallDtoList = new ArrayList<>();
        List<contour> contourList = contourRepository.findAllByImageFile_ImageFileId(imageFileId);

        for(contour contour : contourList){
            List<wall> wallList = wallRepository.findAllByContour_ContourId(contour.getContourId());
            for(wall wall : wallList){
                wallDto wallDto = new wallDto();
                List<point> pointList = pointRepository.findAllByWall_WallId(wall.getWallId());
                wallDto.startPoint = new pointDto(pointList.get(0).getX(),pointList.get(0).getY(), pointType.start);
                wallDto.endPoint =  new pointDto(pointList.get(1).getX(),pointList.get(1).getY(), pointType.end);
                wallDtoList.add(wallDto);
            }
        }
        return wallDtoList;
    }

    private ArrayList<furnitureDto> makeFurnitureDto(Long projectId){

        ArrayList<furnitureDto> furnitureDtoList = new ArrayList<>();
        List<furniture> furnitureList = furnitureRepository.findAllByProject_ProjectId(projectId);
        for(furniture furniture : furnitureList){
            furnitureDto furnitureDto = new furnitureDto();
            furnitureDto.setName(furniture.getName());
            furnitureDto.setX(furniture.getX());
            furnitureDto.setY(furnitureDto.getY());
            furnitureDtoList.add(furnitureDto);
        }
        return furnitureDtoList;
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
