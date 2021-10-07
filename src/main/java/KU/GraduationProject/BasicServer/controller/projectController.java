package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.imageProcessingData.contourDto;
import KU.GraduationProject.BasicServer.dto.projectDto;
import KU.GraduationProject.BasicServer.service.getImageProcessingDataService;
import KU.GraduationProject.BasicServer.service.projectService;
import KU.GraduationProject.BasicServer.service.saveImageProcessingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class projectController {

    @Autowired
    private getImageProcessingDataService getImageProcessingDataService;

    @Autowired
    private saveImageProcessingDataService saveImageProcessingDataService;

    @Autowired
    private projectService projectService;

    @PostMapping("/new")
    public ResponseEntity<Object> createNewProject(@RequestBody projectDto projectDto){
        getImageProcessingDataService.getCoordinate(projectDto.getImageFileId());
        return projectService.createProject(projectDto);
    }



}
