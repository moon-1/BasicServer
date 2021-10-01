package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.projectDto;
import KU.GraduationProject.BasicServer.service.imageProcessingService;
import KU.GraduationProject.BasicServer.service.projectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class projectController {

    @Autowired
    private imageProcessingService imageProcessingService;

    @Autowired
    private projectService projectService;

    @PostMapping("/new")
    public ResponseEntity<Object> createNewProject(@RequestBody projectDto projectDto){
        imageProcessingService.getCoordinate(projectDto.getImageFileId());
        return projectService.createProject(projectDto);
    }



}
