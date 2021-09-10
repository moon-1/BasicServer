package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.service.userService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Map;

@RestController
@Api("User Management API V1")
@RequiredArgsConstructor
public class userController {

    private static final Logger log = LoggerFactory.getLogger(userController.class);

    private final userService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입 시 Id, Role은 자동생성, 이미지는 필수항목아님, 생년월일은 yyyy/mm/dd")
    @PostMapping(value = "/oauth/token")
    public ResponseEntity<OAuth2AccessToken> postAccessToken(
            Principal principal,
            @RequestParam Map<String,String> parameters
    ) throws HttpRequestMethodNotSupportedException, ParseException {
            return userService.joinUser(principal,parameters);
    }

    @ApiOperation(value = "로그인", notes = "토큰으로 요청")
    @GetMapping(value = "/api/me")
    public ResponseEntity<Object> me(Principal principal) {
        return userService.getUser(principal);
    }

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
