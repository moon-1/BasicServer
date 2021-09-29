package KU.GraduationProject.BasicServer.service;

import java.util.Collections;
import java.util.Optional;

import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.domain.entity.account.authority;
import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.dto.userDto;
import KU.GraduationProject.BasicServer.util.securityUtil;
import javassist.bytecode.DuplicateMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class userService {

    private static final Logger log = LoggerFactory.getLogger(userService.class);

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    public Optional<user> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByEmail(username);
    }

    @Transactional(readOnly = true)
    public Optional<user> getMyUserWithAuthorities() {
        return securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }
}
