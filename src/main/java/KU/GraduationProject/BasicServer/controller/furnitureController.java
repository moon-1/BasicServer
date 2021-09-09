package KU.GraduationProject.BasicServer.controller;


import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import KU.GraduationProject.BasicServer.service.furnitureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Area Management API V1")
@RequestMapping("/manage/furniture")
@RequiredArgsConstructor
public class furnitureController {

    private final furnitureService furnitureService;

    @ApiOperation(value = "가구 목록", notes = "전체 가구 목록을 반환함")
    @GetMapping
    public List<furniture> findAll(){
        return furnitureService.findAll();
    }

    @ApiOperation(value = "가구 세부 정보", notes = "해당 가구 대한 상세 정보")
    @GetMapping("/{furnitureId}")
    public furniture findById(@PathVariable Long furnitureId){
        return furnitureService.findById(furnitureId).get();
    }

    @ApiOperation(value = "새로운 가구 추가", notes = "새로운 가구 추가")
    @PostMapping("/add")
    public furniture addFurniture(@ModelAttribute furniture furniture) {
        Long id = furnitureService.save(furniture);
        return furnitureService.findById(id).get();
    }

    @ApiOperation(value = "가구 업데이트", notes = "가구 정보 수정")
    @PostMapping("/{furnitureId}/edit")
    public String editById(@PathVariable Long furnitureId, @ModelAttribute furniture furniture) {
        furnitureService.editById(furnitureId,furniture);
        return "edit user : [" + furnitureId + "]";
    }

    @ApiOperation(value = "가구 삭제", notes = "가구 삭제하기")
    @DeleteMapping("/{furnitureId}/delete")
    public String deleteById(@PathVariable("furnitureId") Long furnitureId, Model model){
        furnitureService.deleteById(furnitureId);
        List<furniture> furniture = furnitureService.findAll();
        model.addAttribute("furniture",furniture);
        return "delete user : [" + furnitureId + "]" ;
    }





}
