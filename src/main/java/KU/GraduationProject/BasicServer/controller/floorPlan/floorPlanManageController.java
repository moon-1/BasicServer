package KU.GraduationProject.BasicServer.controller.floorPlan;

import KU.GraduationProject.BasicServer.domain.entity.floorPlan.*;
import KU.GraduationProject.BasicServer.service.floorPlan.floorPlanManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("Floor Plan API V1")
@RequestMapping("manage/floorPlans")
@RequiredArgsConstructor
public class floorPlanManageController {

//    private final floorPlanManageService floorPlanService;
//
//    @ApiOperation(value = "설계도 목록", notes = "전체 설계도 조회")
//    @GetMapping
//    public List<floorPlan> findAll(){return floorPlanService.findAll();}
//
//    @ApiOperation(value = "설계도 정보", notes = "아이디로 설계도에 대한 상세 정보 조회")
//    @GetMapping("/findById/{id}")
//    public floorPlan findById(@PathVariable Long id){
//        floorPlan floorPlan = floorPlanService.findById(id);
//        return floorPlan;
//    }
//
//    @ApiOperation(value = "설계도 정보", notes = "도면 이름으로 설계도에 대한 상세 정보 조회")
//    @GetMapping("/findByName/{name}")
//    public floorPlan findByFloorPlanName(@PathVariable String name){
//        floorPlan floorPlan = floorPlanService.findByFloorPlanName(name);
//        return floorPlan;
//    }
//
//    @ApiOperation(value = "도면 추가", notes = "새로운 도면 추가")
//    @PostMapping("/saveFloorPlan")
//    public floorPlan saveFloorPlan(@ModelAttribute floorPlan floorPlan) {
//        Long id = floorPlanService.saveFloorPlan(floorPlan);
//        KU.GraduationProject.BasicServer.domain.entity.floorPlan.floorPlan savedFloorPlan = floorPlanService.findById(id);
//        return savedFloorPlan;
//    }

}
