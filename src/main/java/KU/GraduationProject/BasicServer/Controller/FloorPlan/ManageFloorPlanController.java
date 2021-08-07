package KU.GraduationProject.BasicServer.Controller.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.*;
import KU.GraduationProject.BasicServer.Service.FloorPlan.ManageFloorPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static KU.GraduationProject.BasicServer.Domain.FloorPlan.DoorProperty.*;

@RestController
@Api("Floor Plan API V1")
@RequestMapping("manage/floorPlans")
@RequiredArgsConstructor
public class ManageFloorPlanController {

    private final ManageFloorPlanService floorPlanService;

    @ApiOperation(value = "설계도 목록", notes = "전체 설계도 조회")
    @GetMapping
    public List<FloorPlan> findAll(){return floorPlanService.findAll();}

    @ApiOperation(value = "설계도 정보", notes = "아이디로 설계도에 대한 상세 정보 조회")
    @GetMapping("/findById/{id}")
    public FloorPlan findById(@PathVariable Long id){
        FloorPlan floorPlan = floorPlanService.findById(id);
        return floorPlan;
    }

    @ApiOperation(value = "설계도 정보", notes = "도면 이름으로 설계도에 대한 상세 정보 조회")
    @GetMapping("/findByName/{name}")
    public FloorPlan findByFloorPlanName(@PathVariable String name){
        FloorPlan floorPlan = floorPlanService.findByFloorPlanName(name);
        return floorPlan;
    }

    @ApiOperation(value = "도면 추가", notes = "새로운 도면 추가")
    @PostMapping("/saveFloorPlan")
    public FloorPlan saveFloorPlan(@ModelAttribute FloorPlan floorPlan) {
        Long id = floorPlanService.saveFloorPlan(floorPlan);
        FloorPlan savedFloorPlan = floorPlanService.findById(id);
        return savedFloorPlan;
    }

    @PostConstruct
    public void Init(){
        FloorPlan floorPlan = new FloorPlan(1L,"seoul","GuriGalmae","A-6","59A-0530");
        InteriorWall in = new InteriorWall(new Wall(new WallPoint(1.0,2.0),new WallPoint(1.0,7.8)),3);
        List<InteriorWall> interiorWalls = new ArrayList<InteriorWall>();
        interiorWalls.add(in);
        OuterWall out = new OuterWall(new Wall(new WallPoint(4.1,6.2),new WallPoint(1,5.4)),3.3);
        List<OuterWall> outerWalls = new ArrayList<OuterWall>();
        outerWalls.add(out);
        Door doorA = new Door(new Wall(new WallPoint(4.1,6.2),new WallPoint(1,5.4)), bedroom);
        Door doorB = new Door(new Wall(new WallPoint(3.1,2.2),new WallPoint(2.1,3.4)),balcony);
        List<Door> doors = new ArrayList<Door>();
        doors.add(doorA);
        doors.add(doorB);
        floorPlan.setInteriorWall(interiorWalls);
        floorPlan.setOuterWall(outerWalls);
        floorPlan.setDoor(doors);
        floorPlanService.saveFloorPlan(floorPlan);
    }
}
