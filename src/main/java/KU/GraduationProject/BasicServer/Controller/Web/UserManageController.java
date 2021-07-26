package KU.GraduationProject.BasicServer.Controller.Web;

import KU.GraduationProject.BasicServer.DataModel.User;
import KU.GraduationProject.BasicServer.LogicProvider.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/users")
@RequiredArgsConstructor
public class UserManageController {

    private final UserRepository userRepository;

    @GetMapping
    public String Users(Model model){
        List<User> users = userRepository.SearchAll();
        model.addAttribute("users",users);
        return "manage/users";
    }

    @GetMapping("/{userId}")
    public String User(@PathVariable Long userId,Model model){
        User user = userRepository.SearchUser(userId);
        model.addAttribute("user",user);
        return "manage/user";
    }

    @GetMapping("/add")
    public String AddUser() {
        return "manage/addForm";
    }

    @PostMapping("/add")
    public String AddUser(User user, RedirectAttributes redirectAttributes) {

        User savedUser = userRepository.SaveUser(user);
        redirectAttributes.addAttribute("userId", savedUser.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/manage/users/{userId}";
    }

    @GetMapping("/{userId}/edit")
    public String EditUser(@PathVariable Long userId, Model model) {
        User user = userRepository.SearchUser(userId);
        model.addAttribute("user", user);
        return "manage/editForm";
    }

    @PostMapping("/{userId}/edit")
    public String EditUser(@PathVariable Long userId, @ModelAttribute User user) {
        userRepository.UpdateUser(userId, user);
        return "redirect:/manage/users/{userId}";
    }

    @GetMapping("/{userId}/delete")
    public String DeleteUser(@PathVariable Long userId, Model model){
        userRepository.DeleteUser(userId);
        List<User> users = userRepository.SearchAll();
        model.addAttribute("users",users);
        return "manage/users";
    }

    //Init for test
    @PostConstruct
    public void Init(){
        userRepository.SaveUser(new User("userA","aaa",12));
        userRepository.SaveUser(new User("userB","bbb",14));
    }
}
