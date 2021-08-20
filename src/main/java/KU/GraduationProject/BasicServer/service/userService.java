package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.repository.userRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class userService {

    private static final Logger log = LoggerFactory.getLogger(userService.class);
    private final userRepositoryImpl userRepository;

    public Long save(user user){
        userRepository.save(user);
        return user.getUserId();
    }

    public List<user> findAll(){
        List<user> users = new ArrayList<user>();
        try{
            users = userRepository.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return users;
    }

    public Optional<user> findById(Long id){
        Optional<user> user = Optional.empty();
        try{
            user = userRepository.findById(id);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return user;
    }

    public Optional<user> findByUserName(String userName){
        Optional<user> user = Optional.empty();
        try{
            user = userRepository.findByUserName(userName);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return user;
    }

    public void editById(Long userId, user update){
        checkIsUserExist(userId);
        try{
            Optional<user> user = userRepository.findById(userId);
            user.get().setUserName(update.getUserName());
            user.get().setPassword(update.getPassword());
            user.get().setEmail(update.getEmail());
            user.get().setBirth(update.getBirth());
            if(update.getImage()!=null){
                user.get().setImage(update.getImage());
            }
            userRepository.save(user.get());
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    private void checkIsUserExist(Long userId){
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다");
        }
    }

    public void deleteById(Long userId){
        checkIsUserExist(userId);
        try{
            userRepository.deleteById(userId);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    public void deleteAll(){
        if(userRepository.findAll() != null){
            userRepository.deleteAll();
        }
    }

}
