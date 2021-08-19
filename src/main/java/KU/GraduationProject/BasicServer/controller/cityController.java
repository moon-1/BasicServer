package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.city;
import KU.GraduationProject.BasicServer.service.cityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("City Management API V1")
@RequestMapping("/manage/city")
@RequiredArgsConstructor
public class cityController {

    private final cityService cityService;

    @ApiOperation(value = "도시 목록", notes = "전체 도시 목록을 반환함")
    @GetMapping
    public List<city> findAll(){
        return cityService.findAll();
    }

    @ApiOperation(value = "사용자 정보", notes = "사용자에 대한 상세 정보")
    @GetMapping("/{cityId}")
    public city findById(@PathVariable Long cityId){
        return cityService.findById(cityId).get();
    }

    @ApiOperation(value = "도시 목록 업데이트", notes = "DB에 도시 데이터 업데이트")
    @GetMapping("/update")
    public List<city> updateCityList(){
        return cityService.updateCityList();
    }

    @ApiOperation(value = "전체 삭제", notes = "도시 목록 전체 삭제")
    @DeleteMapping("/delete")
    public String deleteAll(){
        cityService.deleteAll();
        return "delete all" ;
    }

}
