package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.project.project;
import KU.GraduationProject.BasicServer.service.projectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
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

    @ApiOperation(value = "해당 사용자의 프로젝트 정보", notes = "사용자별 프로젝트에 대한 상세 정보")
    @GetMapping("/user/{userId}")
    public List<project> findByUserId(@PathVariable Long userId){
        return projectService.findByUserId(userId);
    }

    @ApiOperation(value = "해당 도면의 프로젝트 정보", notes = "도면별 프로젝트에 대한 상세 정보")
    @GetMapping("/floorPlan/{floorPlanId}")
    public List<project> findByFloorPlanId(@PathVariable Long floorPlanId){
        return projectService.findByFloorPlanId(floorPlanId);
    }

    @ApiOperation(value = "새로운 프로젝트 시작", notes = "프로젝트 생성, 날짜는 생성하는 날짜로 자동 업데이트")
    @PostMapping("/add")
    public project addProject(@ModelAttribute project project) {
        Long id = projectService.save(project);
        return projectService.findById(id).get();
    }

    @ApiOperation(value = "프로젝트 업데이트", notes = "프로젝트 정보 업데이트, 날짜는 업데이트하는 날짜로 자동 업데이트")
    @PostMapping("/{projectId}/edit")
    public String editById(@PathVariable Long projectId, @ModelAttribute project project) {
        projectService.editById(projectId,project);
        return "edit user : [" + projectId + "]";
    }

    @ApiOperation(value = "프로젝트 삭제", notes = "프로젝트 삭제하기")
    @DeleteMapping("/{projectId}/delete")
    public String deleteById(@PathVariable("projectId") Long projectId, Model model){
        projectService.deleteById(projectId);
        List<project> projects = projectService.findAll();
        model.addAttribute("projects",projects);
        return "delete user : [" + projectId + "]" ;
    }

    @ApiOperation(value = "프로젝트 전체 삭제", notes = "프로젝트 전부 삭제하기")
    @DeleteMapping("/deleteALL")
    public String deleteAll(){
        projectService.deleteAll();
        return "delete all" ;
    }
}
