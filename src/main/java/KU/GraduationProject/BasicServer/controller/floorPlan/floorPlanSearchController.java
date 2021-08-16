package KU.GraduationProject.BasicServer.controller.floorPlan;

import KU.GraduationProject.BasicServer.service.floorPlan.floorPlanSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Floor Plan Path V1")
@RequestMapping("search/floorPlans")
@RequiredArgsConstructor
public class floorPlanSearchController {

    private floorPlanSearchService searchFloorPlanService;

    @ApiOperation(value = "도시 목록", notes = "도시 목록 제공")
    @GetMapping("/cityList")
    public List<String> cityListProvider(){
        return searchFloorPlanService.cityListProvider();
    }

    @ApiOperation(value = "도면 목록", notes = "선택한 도시의 도면 목록 제공")
    @GetMapping("/{city}/floorPlanList")
    public List<String> floorPlanListProvider(@PathVariable String city){
        return searchFloorPlanService.floorPlanListProvider(city);
    }

    @ApiOperation(value = "도면 이미지", notes = "선택한 도면 이미지 제공")
    @GetMapping("floorPlanImage/{city}/{fileName}")
    public String floorPlanImageProvider(@PathVariable String city,@PathVariable String fileName){
        return searchFloorPlanService.floorPlanJsonFileReader(city,fileName);
    }
}
