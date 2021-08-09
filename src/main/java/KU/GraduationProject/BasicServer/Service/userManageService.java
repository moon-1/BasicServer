package KU.GraduationProject.BasicServer.Service;

import KU.GraduationProject.BasicServer.Domain.user;
import KU.GraduationProject.BasicServer.Interface.Repository.userManageRepositoryImpl;
import KU.GraduationProject.BasicServer.Service.FloorPlan.floorPlanSearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class userManageService {

    private static final Logger log = LoggerFactory.getLogger(userManageService.class);
    private final userManageRepositoryImpl userRepository;

    public Long save(user user){
        checkDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void checkDuplicateUser(user user){
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalStateException("중복되는 아이디 입니다.");
        }
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

    public user findById(Long id){
        user user = new user();
        try{
            user = userRepository.findById(id);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return user;
    }

    public user findByUserName(String userName){
        user user = new user();
        try{
            user = userRepository.findByUserName(userName);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return user;
    }

    public void editById(Long userId, user updateParameter){
        checkIsUserExist(userId);
        try{
            userRepository.editById(userId,updateParameter);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    private void checkIsUserExist(Long userId){
        if (userRepository.findById(userId) == null) {
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
