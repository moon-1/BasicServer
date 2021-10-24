package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.modelDto.model3DDto;
import KU.GraduationProject.BasicServer.service.project.model3DService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class model3DController {

    private final model3DService model3DService;

    @PostMapping("/save/model")
    public ResponseEntity<Object> update3DModel(@RequestBody model3DDto model3DDto){
        return model3DService.save3DModel(model3DDto);
    }

}
