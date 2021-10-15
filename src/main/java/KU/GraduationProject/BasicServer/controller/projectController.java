package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.imageProcessingData.contourDto;
import KU.GraduationProject.BasicServer.dto.projectDto;
import KU.GraduationProject.BasicServer.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class projectController {

    private final getImageProcessingDataService getImageProcessingDataService;

    private final projectService projectService;

    private final makeContainerService makeContainerService;

    @ExceptionHandler(MultipartException.class)
    @PostMapping("/new")
    public ResponseEntity<Object> createNewProject(@RequestBody projectDto projectDto) throws JsonProcessingException {
        getImageProcessingDataService.getCoordinate(projectDto.getImageFileId());
        makeContainerService.runImageProcessingServerShellScript();
        return projectService.createProject(projectDto);
    }



}
