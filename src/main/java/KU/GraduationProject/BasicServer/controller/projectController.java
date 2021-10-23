package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.projectDto.newProjectDto;
import KU.GraduationProject.BasicServer.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class projectController {

    private final getImageProcessingDataService getImageProcessingDataService;

    private final projectService projectService;

    private final makeContainerService makeContainerService;

    @ExceptionHandler(MultipartException.class)
    @PostMapping("/new")
    public ResponseEntity<Object> createNewProject(@RequestBody newProjectDto newProjectDto) throws JsonProcessingException {
        getImageProcessingDataService.getCoordinate(newProjectDto.getImageFileId());
        makeContainerService.runImageProcessingServerShellScript();
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
