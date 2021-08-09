package KU.GraduationProject.BasicServer.Controller;

import KU.GraduationProject.BasicServer.Domain.user;
import KU.GraduationProject.BasicServer.Service.userManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Api("User Management API V1")
@RequestMapping("/manage/users")
@RequiredArgsConstructor
public class userManageController {

    private final userManageService userService;

    @ApiOperation(value = "사용자 목록", notes = "회원 전체 목록을 반환함")
    @GetMapping
    public List<user> findAll(){
        return userService.findAll();
    }

    @ApiOperation(value = "사용자 정보", notes = "사용자에 대한 상세 정보")
    @GetMapping("/{userId}")
    public user findById(@PathVariable Long userId){
        user user = userService.findById(userId);
        return user;
    }

    @ApiOperation(value = "회원가입", notes = "새로운 사용자 추가")
    @PostMapping("/add")
    public user addUser(@ModelAttribute user user) {
        Long id = userService.save(user);
        user savedUser = userService.findById(id);
        return savedUser;
    }

    @ApiOperation(value = "회원 정보 수정", notes = "사용자 정보 수정")
    @PostMapping("/{userId}/edit")
    public String editById(@PathVariable Long userId, @ModelAttribute user user) {
        userService.editById(userId, user);
        return "redirect:/manage/users/{userId}";
    }

    @ApiOperation(value = "탈퇴", notes = "사용자 정보 삭제")
    @DeleteMapping("/{userId}/delete")
    public String deleteById(@PathVariable("userId") Long userId, Model model){
        userService.deleteById(userId);
        List<user> users = userService.findAll();
        model.addAttribute("users",users);
        return "manage/users";
    }

    //Init for test
    @PostConstruct
    public void init(){
        userService.save(new user("userA","aaa",12));
        userService.save(new user("userB","bbb",14));
    }
}
