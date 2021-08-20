package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.floorPlan;
import KU.GraduationProject.BasicServer.service.floorPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Floor Plan Management API V1")
@RequestMapping("/manage/floorPlan")
@RequiredArgsConstructor
public class floorPlanController {

    private final floorPlanService floorPlanService;

    @ApiOperation(value = "전체 도면 조회", notes = "전체 도면 목록을 반환함")
    @GetMapping
    public List<floorPlan> findAll(){
        return floorPlanService.findAll();
    }

    @ApiOperation(value = "구역 내 도면 목록", notes = "해당 도시의 구역 목록을 반환함")
    @GetMapping("/{areaId}")
    public List<floorPlan> findByArea(@PathVariable Long areaId){
        return floorPlanService.findByAreaId(areaId);
    }

    @ApiOperation(value = "구역별 도면 정보 업데이트", notes = "DB에 전체 도면 데이터 업데이트")
    @GetMapping("/update")
    public List<floorPlan> updateFloorPlan(){
        return floorPlanService.updateFloorPlan();
    }

    @ApiOperation(value = "전체 삭제", notes = "도면 목록 전체 삭제")
    @DeleteMapping("/delete")
    public String deleteAll(){
        floorPlanService.deleteAll();
        return "delete all" ;
    }
}
