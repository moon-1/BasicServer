package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.district.area;
import KU.GraduationProject.BasicServer.service.areaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Area Management API V1")
@RequestMapping("/manage/area")
@RequiredArgsConstructor
public class areaController {

    private final areaService areaService;

    @ApiOperation(value = "전체 구역 조회", notes = "전체 구역 목록을 반환함")
    @GetMapping("/findAll")
    public List<area> findAll(){
        return areaService.findAll();
    }

    @ApiOperation(value = "도시의 구역 목록", notes = "해당 도시의 구역 목록을 반환함")
    @GetMapping("/{cityId}")
    public List<area> findByCity(@PathVariable Long cityId){
        return areaService.findByCity(cityId);
    }

    @ApiOperation(value = "도시별 구역 목록 업데이트", notes = "DB에 도시 데이터 업데이트,     " +"path 는 '/'대신 '@'구분자로 경로입력")
    @GetMapping("/update/{path}")
    public List<area> updateAreaList(@PathVariable String path){
        return areaService.updateArea(path);
    }

    @ApiOperation(value = "전체 삭제", notes = "도시 구역 목록 전체 삭제")
    @DeleteMapping("/delete")
    public String deleteAll(){
        areaService.deleteAll();
        return "delete all" ;
    }
}
