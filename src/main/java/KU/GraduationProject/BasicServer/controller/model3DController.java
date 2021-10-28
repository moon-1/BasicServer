package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.modelDto.model3DDto;
import KU.GraduationProject.BasicServer.service.project.modelHandlingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class model3DController {

    private final modelHandlingService modelHandlingService;

    @PostMapping("/save/model")
    public ResponseEntity<Object> update3DModel(@RequestBody model3DDto model3DDto){
        return modelHandlingService.save3DModel(model3DDto);
    }

    @GetMapping("/check/{id}/exist")
    public ResponseEntity<Object> checkIs3DModelExist(@PathVariable Long id){
        return modelHandlingService.checkIs3DModelExist(id);
    }

}
