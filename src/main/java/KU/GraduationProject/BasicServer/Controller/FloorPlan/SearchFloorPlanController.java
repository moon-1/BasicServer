package KU.GraduationProject.BasicServer.Controller.FloorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.FloorPlan;
import KU.GraduationProject.BasicServer.Service.FloorPlan.SearchFloorPlanService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api("Floor Plan Path V1")
@RequestMapping("search/floorPlans")
@RequiredArgsConstructor
public class SearchFloorPlanController {

    private final SearchFloorPlanService searchFloorPlanService;

    @GetMapping("/cityList")
    public Map<String, List<String>> cityListProvider(){
        return searchFloorPlanService.cityListProvider();
    }

    @GetMapping("/{city}/floorPlanList")
    public Map<String, List<String>> floorPlanListProvider(@PathVariable String city){
        return searchFloorPlanService.floorPlanListProvider(city);
    }

}
