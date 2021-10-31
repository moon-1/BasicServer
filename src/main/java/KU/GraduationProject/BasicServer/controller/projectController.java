package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.projectDto.newProjectDto;
import KU.GraduationProject.BasicServer.dto.modelDto.wallPlotLengthDto;
import KU.GraduationProject.BasicServer.dto.projectDto.saveProjectDto;
import KU.GraduationProject.BasicServer.service.project.modelHandlingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class projectController {

    private final KU.GraduationProject.BasicServer.service.project.projectService projectService;

    private final modelHandlingService modelHandlingService;

    private final KU.GraduationProject.BasicServer.service.docker.makeContainerService makeContainerService;

    @ExceptionHandler(MultipartException.class)
    @PostMapping("/new")
    public ResponseEntity<Object> createNewProject(@RequestBody newProjectDto newProjectDto) throws JsonProcessingException {
        //makeContainerService.runImageProcessingServerShellScript();
        return projectService.createProject(newProjectDto);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveProject(@RequestBody saveProjectDto saveProjectDto){
        return projectService.saveProject(saveProjectDto);
    }

    @PostMapping("/getAIProcessingData")
    public void AIServerDataHandling(@RequestBody wallPlotLengthDto wallPlotLengthDto){
        modelHandlingService.getWallPlotLength(wallPlotLengthDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Object> showProjectList(){
        return projectService.showProjectList();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id){
        return projectService.deleteProject(id);
    }

    @GetMapping("/{id}/open")
    public ResponseEntity<Object> openProject(@PathVariable Long id){ return projectService.openProject(id); }
}
