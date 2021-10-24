package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.accountDto.checkPasswordDto;
import KU.GraduationProject.BasicServer.dto.accountDto.loginDto;
import KU.GraduationProject.BasicServer.dto.accountDto.updateUserInfoDto;
import KU.GraduationProject.BasicServer.dto.accountDto.userDto;
import KU.GraduationProject.BasicServer.service.account.userService;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class userController {

    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(
            @Valid @RequestBody userDto userDto
    ) throws DuplicateMemberException {
        return userService.signup(userDto);
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<Object> withdrawal(@Valid @RequestBody checkPasswordDto checkPasswordDto){
        return userService.withdrawal(checkPasswordDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authorize(@Valid @RequestBody loginDto loginDto) {
       return userService.accessTokenProvider(loginDto);
    }

    @PostMapping("/check/password")
    public ResponseEntity<Object> checkPassword(@Valid @RequestBody checkPasswordDto checkPasswordDto) {
        return userService.checkPassword(checkPasswordDto);
    }

    @PutMapping("/update/userInfo")
    public ResponseEntity<Object> update(@Valid @RequestBody updateUserInfoDto updateUserInfoDto){
        return userService.updateUserInfo(updateUserInfoDto);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Object> getMyUserInfo(HttpServletRequest request) {
        return userService.getMyUserWithAuthorities();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getUserInfo(@PathVariable String username) {
        return userService.getUserWithAuthorities(username);
    }
}
