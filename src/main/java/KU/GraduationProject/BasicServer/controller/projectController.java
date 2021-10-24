package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.projectDto.newProjectDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class projectController {

    private final KU.GraduationProject.BasicServer.service.dataProcessing.getImageProcessingDataService getImageProcessingDataService;

    private final KU.GraduationProject.BasicServer.service.dataProcessing.getAIProcessingDataService getAIProcessingDataService;

    private final KU.GraduationProject.BasicServer.service.project.projectService projectService;

    private final KU.GraduationProject.BasicServer.service.docker.makeContainerService makeContainerService;

    @ExceptionHandler(MultipartException.class)
    @PostMapping("/new")
    public ResponseEntity<Object> createNewProject(@RequestBody newProjectDto newProjectDto) throws JsonProcessingException {
        //makeContainerService.runImageProcessingServerShellScript();
        getImageProcessingDataService.getCoordinate(newProjectDto.getImageFileId());
        //getAIProcessingDataService.getWallPlotLength(newProjectDto.getImageFileId());
        return projectService.createProject(newProjectDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Object> showProjectList(){
        return projectService.showProjectList();
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id){
        return projectService.deleteProject(id);
    }
}
