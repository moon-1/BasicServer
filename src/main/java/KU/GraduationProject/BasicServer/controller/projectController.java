package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.project;
import KU.GraduationProject.BasicServer.domain.entity.user;
import KU.GraduationProject.BasicServer.service.projectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Project Management API V1")
@RequestMapping("/manage/project")
@RequiredArgsConstructor
public class projectController {

    private final projectService projectService;

    @ApiOperation(value = "프로젝트 목록", notes = "프로젝트 전체 목록을 반환함")
    @GetMapping
    public List<project> findAll(){
        return projectService.findAll();
    }

    @ApiOperation(value = "프로젝트 정보", notes = "프로젝트에 대한 상세 정보")
    @GetMapping("/{projectId}")
    public project findById(@PathVariable Long projectId){
        return projectService.findById(projectId).get();
    }

    @ApiOperation(value = "프로젝트 정보", notes = "프로젝트에 대한 상세 정보")
    @GetMapping("/user/{userId}")
    public List<project> findByUserId(@PathVariable Long userId){
        return projectService.findByUserId(userId);
    }

    @ApiOperation(value = "새로운 프로젝트 시작", notes = "프로젝트 추가")
    @PostMapping("/add")
    public project addProject(@ModelAttribute project project) {
        Long id = projectService.save(project);
        return projectService.findById(id).get();
    }
}
