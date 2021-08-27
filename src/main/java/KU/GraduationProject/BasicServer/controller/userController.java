package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.repository.userRepositoryImpl;
import KU.GraduationProject.BasicServer.service.auth.jwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Api("User Management API V1")
@RequestMapping("/manage/users")
@RequiredArgsConstructor
public class userController {

    private final KU.GraduationProject.BasicServer.service.userService userService;

    private final PasswordEncoder passwordEncoder;
    private final jwtTokenProvider jwtTokenProvider;
    private final userRepositoryImpl userRepository;

    @ApiOperation(value = "회원가입", notes = "회원가입 시 Id, Role은 자동생성, 이미지는 필수항목아님, 생년월일은 yyyy/mm/dd")
    @PostMapping("/join")
    public String join(@ModelAttribute user userData) {
        try{
            return userRepository.save(user.builder()
                    .email(userData.getEmail())
                    .password(passwordEncoder.encode(userData.getPassword()))
                    .birth(userData.getBirth())
                    .image(userData.getImage())
                    .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                    .build()).getId().toString();
        }
        catch(Exception ex){
            return "이메일, 비밀번호, 생년월일은 필수 항목 입니다.";
        }
    }

    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호만 필요")
    @PostMapping("/login")
    public String login(@ModelAttribute user userData) {
        user user = new user();
        try{
            user = userRepository.findByEmail(userData.getEmail()).get();
        }
        catch(Exception ex){
            return "사용자 계정이 존재하지 않습니다.";
        }
        if (!passwordEncoder.matches(userData.getPassword(), user.getPassword())) {
            //throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            return "잘못된 비밀번호 입니다.";
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }
//
//    @ApiOperation(value = "사용자 목록", notes = "회원 전체 목록을 반환함")
//    @GetMapping("/admin")
//    public List<user> findAll(){
//        return userService.findAll();
//    }
//
//    @ApiOperation(value = "사용자 정보", notes = "사용자에 대한 상세 정보")
//    @GetMapping("/{userId}")
//    public user findById(@PathVariable Long userId){
//        return userService.findById(userId).get();
//    }
//
//    @ApiOperation(value = "회원 정보 수정", notes = "사용자 정보 수정, 출생년도의 경우 'yyyy/mm/dd' 포맷으로 입력")
//    @PostMapping("/{userId}/edit")
//    public String editById(@PathVariable Long userId, @ModelAttribute user user) {
//        userService.editById(userId, user);
//        return "edit user : [" + userId + "]";
//    }
//
//    @ApiOperation(value = "탈퇴", notes = "사용자 정보 삭제")
//    @DeleteMapping("/{userId}/delete")
//    public String deleteById(@PathVariable("userId") Long userId, Model model){
//        userService.deleteById(userId);
//        List<user> users = userService.findAll();
//        model.addAttribute("users",users);
//        return "delete user : [" + userId + "]" ;
//    }

}
