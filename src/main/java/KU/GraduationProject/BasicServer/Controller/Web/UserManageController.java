package KU.GraduationProject.BasicServer.Controller.Web;

import KU.GraduationProject.BasicServer.Domain.User;
import KU.GraduationProject.BasicServer.Interface.Repository.UserRepositoryImpl;
import KU.GraduationProject.BasicServer.Repository.UserRepository;
import KU.GraduationProject.BasicServer.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Api("User Management API V1")
@RequestMapping("/manage/users")
@RequiredArgsConstructor
public class UserManageController {

    private final UserService userService;

    @ApiOperation(value = "사용자 목록", notes = "회원 전체 목록을 반환함")
    @GetMapping
    public List<User> findAll(){
        return userService.SearchAll();
    }

    @ApiOperation(value = "사용자 정보", notes = "사용자에 대한 상세 정보")
    @GetMapping("/{userId}")
    public User findById(@PathVariable Long userId){
        User user = userService.SearchUser(userId);
        return user;
    }

    @ApiOperation(value = "회원가입", notes = "새로운 사용자 추가")
    @PostMapping("/add")
    public User AddUser(@ModelAttribute User user) {
        Long id = userService.SaveUser(user);
        User savedUser = userService.SearchUser(id);
        return savedUser;
    }

    @ApiOperation(value = "회원 정보 수정", notes = "사용자 정보 수정")
    @PostMapping("/{userId}/edit")
    public String editById(@PathVariable Long userId, @ModelAttribute User user) {
        userService.UpdateUser(userId, user);
        return "redirect:/manage/users/{userId}";
    }

    @ApiOperation(value = "탈퇴", notes = "사용자 정보 삭제")
    @DeleteMapping("/{userId}/delete")
    public String deleteById(@PathVariable("userId") Long userId, Model model){
        userService.DeleteUser(userId);
        List<User> users = userService.SearchAll();
        model.addAttribute("users",users);
        return "manage/users";
    }

    //Init for test
    @PostConstruct
    public void Init(){
        userService.SaveUser(new User("userA","aaa",12));
        userService.SaveUser(new User("userB","bbb",14));
    }
}
