package KU.GraduationProject.BasicServer.controller.floorPlan;

import KU.GraduationProject.BasicServer.Service.floorPlan.floorPlanSearchService;
import io.swagger.annotations.Api;
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

    private final floorPlanSearchService searchFloorPlanService;

    @GetMapping("/cityList")
    public List<String> cityListProvider(){
        return searchFloorPlanService.cityListProvider();
    }

    @GetMapping("/{city}/floorPlanList")
    public List<String> floorPlanListProvider(@PathVariable String city){
        return searchFloorPlanService.floorPlanListProvider(city);
    }

    @GetMapping("floorPlanImage/{city}/{fileName}")
    public String floorPlanImageProvider(@PathVariable String city,@PathVariable String fileName){
        return searchFloorPlanService.floorPlanJsonFileReader(city,fileName);
    }
}
