package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.entity.response.defaultResult;
import KU.GraduationProject.BasicServer.domain.entity.response.responseMessage;
import KU.GraduationProject.BasicServer.domain.entity.response.statusCode;
import KU.GraduationProject.BasicServer.domain.repository.userRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class userService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(userService.class);
    private final userRepositoryImpl userRepository;

    private final TokenEndpoint tokenEndpoint;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public ResponseEntity joinUser(Principal principal, Map<String,String> parameters) throws ParseException, HttpRequestMethodNotSupportedException {

        try{
            if(checkIsExist(parameters.get("username")))
            {
                return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.DUPLICATED_USER,parameters.get("username")), HttpStatus.OK);
            }
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user user = new user();
            Date birth = transFormat.parse(parameters.get("birth"));
            user.setEmail(parameters.get("username"));
            user.setPassword(passwordEncoder.encode(parameters.get("password")));
            user.setRoles(Collections.singletonList("ROLE_USER"));
            user.setBirth(birth);
            userRepository.save(user);
        }
        catch(ParseException pex){
            log.error(pex.getMessage());
            return new ResponseEntity(defaultResult.res(statusCode.BAD_REQUEST, responseMessage.BAD_REQUEST,pex.getMessage()), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR, responseMessage.INTERNAL_SERVER_ERROR,parameters), HttpStatus.OK);
        }
        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.CREATED_USER,tokenEndpoint.postAccessToken(principal, parameters)), HttpStatus.OK);
    }

    public ResponseEntity getUser(Principal principal) {
        if(!checkIsExist(principal.getName()))
        {
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.NOT_FOUND_USER,principal.getName()), HttpStatus.OK);
        }
        Optional<user> user;
        try {
            user = userRepository.findByEmail(principal.getName());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity(defaultResult.res(statusCode.DB_ERROR, responseMessage.DB_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.LOGIN_SUCCESS, user), HttpStatus.OK);
    }

    private boolean checkIsExist(String email){
        if (userRepository.existsByEmail(email)){
            return true;
        }
        return false;
    }
//
//
//    public Optional<user> findById(Long id){
//        Optional<user> user = Optional.empty();
//        try{
//            user = userRepository.findById(id);
//        }
//        catch(Exception ex){
//            log.error(ex.getMessage());
//        }
//        return user;
//    }
//
//    public Optional<user> findByEmail(String userName){
//        Optional<user> user = Optional.empty();
//        try{
//            user = userRepository.findByEmail(userName);
//        }
//        catch(Exception ex){
//            log.error(ex.getMessage());
//        }
//        return user;
//    }
//
//    public void editById(Long userId, user update){
//        checkIsUserExist(userId);
//        try{
//            Optional<user> user = userRepository.findById(userId);
//            user.get().setPassword(update.getPassword());
//            user.get().setEmail(update.getEmail());
//            user.get().setBirth(update.getBirth());
//            if(update.getImage()!=null){
//                user.get().setImage(update.getImage());
//            }
//            userRepository.save(user.get());
//        }
//        catch(Exception ex){
//            log.error(ex.getMessage());
//        }
//    }
//
//    private void checkIsUserExist(Long userId){
//        if (userRepository.existsById(userId)) {
//            throw new IllegalStateException("존재하지 않는 사용자 입니다");
//        }
//    }
//
//    public void deleteById(Long userId){
//        checkIsUserExist(userId);
//        try{
//            userRepository.deleteById(userId);
//        }
//        catch(Exception ex){
//            log.error(ex.getMessage());
//        }
//    }
//
//    public List<user> findAll(){
//        List<user> users = new ArrayList<user>();
//        try{
//            users = userRepository.findAll();
//        }
//        catch(Exception ex){
//            log.error(ex.getMessage());
//        }
//        return users;
//    }
//
//    public void deleteAll(){
//        if(userRepository.findAll() != null){
//            userRepository.deleteAll();
//        }
//    }

}
