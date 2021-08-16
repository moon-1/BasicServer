package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.area;
import KU.GraduationProject.BasicServer.domain.entity.city;
import KU.GraduationProject.BasicServer.service.areaManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("City Management API V1")
@RequestMapping("/manage/area")
@RequiredArgsConstructor
public class areaManageController {

    private final areaManageService areaManageService;

    @ApiOperation(value = "도시 구역 목록", notes = "해당 도시의 구역 목록을 반환함")
    @GetMapping("/{cityId}")
    public List<area> findByCity(@PathVariable Long cityId){
        return areaManageService.findByCity(cityId);
    }

    @ApiOperation(value = "도시별 구역 목록 업데이트", notes = "DB에 도시 데이터 업데이트")
    @GetMapping("/update")
    public List<area> updateAreaList(){
        return areaManageService.updateArea();
    }
}
