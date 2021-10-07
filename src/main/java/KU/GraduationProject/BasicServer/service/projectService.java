package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import KU.GraduationProject.BasicServer.domain.repository.projectRepository;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.dto.createdProjectDto;
import KU.GraduationProject.BasicServer.dto.projectDto;
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
import java.util.Date;

@Service
public class projectService {

    @Autowired
    private projectRepository projectRepository;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

    @Autowired
    private userRepository userRepository;

    public ResponseEntity<Object> createProject(projectDto projectDto){
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            imageFile imageFile = imageFileRepository.findById(projectDto.getImageFileId()).get();
            if(imageFile==null){
                return new ResponseEntity(defaultResult.res(statusCode.NOT_FOUND, responseMessage.IMAGE_NOT_FOUND, "project name :"+ projectDto.getName()),
                        HttpStatus.OK);
            }
            else if(imageFile.getUser() != userInfo.get()){
                return new ResponseEntity(defaultResult.res(statusCode.FORBIDDEN, responseMessage.FORBIDDEN_IMAGE, "Image file id :"+ imageFile.getImageFileId()),
                        HttpStatus.OK);
            }
            project project = KU.GraduationProject.BasicServer.domain.entity.project.project.builder()
                    .date(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .name(projectDto.getName())
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
}
