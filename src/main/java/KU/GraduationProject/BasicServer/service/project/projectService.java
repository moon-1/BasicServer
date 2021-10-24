package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import KU.GraduationProject.BasicServer.domain.repository.projectRepository;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.dto.createdProjectDto;
import KU.GraduationProject.BasicServer.dto.projectDto.newProjectDto;
import KU.GraduationProject.BasicServer.dto.projectDto.projectListDto;
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
    private userRepository userRepository;

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
