package KU.GraduationProject.BasicServer.service.account;

import java.util.Collections;

import KU.GraduationProject.BasicServer.dto.accountDto.*;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.domain.entity.account.authority;
import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.jwt.jwtFilter;
import KU.GraduationProject.BasicServer.jwt.tokenProvider;
import KU.GraduationProject.BasicServer.util.securityUtil;
import javassist.bytecode.DuplicateMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class userService {

    private static final Logger log = LoggerFactory.getLogger(userService.class);

    @Autowired
    private tokenProvider tokenProvider;
    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public userService(userRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<Object> signup(userDto userDto) throws DuplicateMemberException {
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            log.error("이미 가입되어 있는 유저입니다.");
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.DUPLICATED_USER,userDto), HttpStatus.OK);

        }

        authority authority = KU.GraduationProject.BasicServer.domain.entity.account.authority.builder()
                .authorityName("ROLE_USER")
                .build();

        user user = KU.GraduationProject.BasicServer.domain.entity.account.user.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .birth(userDto.getBirth())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.CREATED_USER,userRepository.save(user)), HttpStatus.OK);

    }

    public ResponseEntity<Object> accessTokenProvider(loginDto loginDto){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(jwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.ACCESS_TOKEN_SUCCESS,new tokenDto(jwt)), HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<Object> withdrawal(checkPasswordDto checkPasswordDto) {
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            if(passwordEncoder.matches(checkPasswordDto.getPassword(),userInfo.get().getPassword()))
            {
                userRepository.delete(userInfo.get());
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.DELETE_USER,
                        "User [ " + userInfo.get().getNickname() + " ] is deleted"), HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.UNAUTHORIZED, responseMessage.WRONG_PASSWORD), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.BAD_REQUEST, responseMessage.INVALID_TOKEN), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<Object> checkPassword(checkPasswordDto checkPasswordDto) {
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            if(passwordEncoder.matches(checkPasswordDto.getPassword(),userInfo.get().getPassword()))
            {
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.CORRECT_PASSWORD), HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.UNAUTHORIZED, responseMessage.WRONG_PASSWORD), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.BAD_REQUEST, responseMessage.INVALID_TOKEN), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> updateUserInfo(updateUserInfoDto updateUserInfoDto){

        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            if(passwordEncoder.matches(updateUserInfoDto.getCurrentPassword(),userInfo.get().getPassword()))
            {
                userInfo.get().setPassword(passwordEncoder.encode(updateUserInfoDto.getNewPassword()));
                userInfo.get().setNickname(updateUserInfoDto.getNickname());
                userInfo.get().setBirth(updateUserInfoDto.getBirth());
                userRepository.save(userInfo.get());
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPDATE_USER,updateUserInfoDto), HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.UNAUTHORIZED, responseMessage.WRONG_PASSWORD), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.BAD_REQUEST, responseMessage.INVALID_TOKEN), HttpStatus.OK);
        }
    }


    @Transactional(readOnly = true)
    public ResponseEntity<Object> getUserWithAuthorities(String username) {
        try{
            user user = userRepository.findOneWithAuthoritiesByEmail(username).get();
            if(user == null){
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.NOT_FOUND_USER,username), HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.READ_USER,user), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.UNAUTHORIZED, responseMessage.INVALID_TOKEN,username), HttpStatus.OK);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getMyUserWithAuthorities() {
        try{
            user user = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail).get();
            if(user == null){
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.NOT_FOUND_USER), HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.READ_USER,user), HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.UNAUTHORIZED, responseMessage.INVALID_TOKEN), HttpStatus.OK);
        }
    }
}
