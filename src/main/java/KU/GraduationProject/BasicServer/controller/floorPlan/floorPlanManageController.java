package KU.GraduationProject.BasicServer.controller.floorPlan;

import KU.GraduationProject.BasicServer.domain.entity.floorPlan.*;
import KU.GraduationProject.BasicServer.Service.floorPlan.floorPlanManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static KU.GraduationProject.BasicServer.domain.entity.floorPlan.doorProperty.*;

@RestController
@Api("Floor Plan API V1")
@RequestMapping("manage/floorPlans")
@RequiredArgsConstructor
public class floorPlanManageController {

    private final floorPlanManageService floorPlanService;

    @ApiOperation(value = "설계도 목록", notes = "전체 설계도 조회")
    @GetMapping
    public List<floorPlan> findAll(){return floorPlanService.findAll();}

    @ApiOperation(value = "설계도 정보", notes = "아이디로 설계도에 대한 상세 정보 조회")
    @GetMapping("/findById/{id}")
    public floorPlan findById(@PathVariable Long id){
        floorPlan floorPlan = floorPlanService.findById(id);
        return floorPlan;
    }

    @ApiOperation(value = "설계도 정보", notes = "도면 이름으로 설계도에 대한 상세 정보 조회")
    @GetMapping("/findByName/{name}")
    public floorPlan findByFloorPlanName(@PathVariable String name){
        floorPlan floorPlan = floorPlanService.findByFloorPlanName(name);
        return floorPlan;
    }

    @ApiOperation(value = "도면 추가", notes = "새로운 도면 추가")
    @PostMapping("/saveFloorPlan")
    public floorPlan saveFloorPlan(@ModelAttribute floorPlan floorPlan) {
        Long id = floorPlanService.saveFloorPlan(floorPlan);
        KU.GraduationProject.BasicServer.domain.entity.floorPlan.floorPlan savedFloorPlan = floorPlanService.findById(id);
        return savedFloorPlan;
    }

    @PostConstruct
    public void Init(){
        floorPlan floorPlan = new floorPlan(1L,"seoul","GuriGalmae","A-6","59A-0530");
        interiorWall in = new interiorWall(new wall(new wallPoint(1.0,2.0),new wallPoint(1.0,7.8)),3);
        List<interiorWall> interiorWalls = new ArrayList<interiorWall>();
        interiorWalls.add(in);
        outerWall out = new outerWall(new wall(new wallPoint(4.1,6.2),new wallPoint(1,5.4)),3.3);
        List<outerWall> outerWalls = new ArrayList<outerWall>();
        outerWalls.add(out);
        door doorA = new door(new wall(new wallPoint(4.1,6.2),new wallPoint(1,5.4)), bedroom);
        door doorB = new door(new wall(new wallPoint(3.1,2.2),new wallPoint(2.1,3.4)),balcony);
        List<door> doors = new ArrayList<door>();
        doors.add(doorA);
        doors.add(doorB);
        floorPlan.setInteriorWall(interiorWalls);
        floorPlan.setOuterWall(outerWalls);
        floorPlan.setDoor(doors);
        floorPlanService.saveFloorPlan(floorPlan);
    }
}
