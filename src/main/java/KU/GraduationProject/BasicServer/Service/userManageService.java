package KU.GraduationProject.BasicServer.Service;

import KU.GraduationProject.BasicServer.Domain.user;
import KU.GraduationProject.BasicServer.Interface.Repository.userManageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userManageService {

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
        return userRepository.findAll();
    }

    public user findById(Long id){
        return userRepository.findById(id);
    }

    public user findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void editById(Long userId, user updateParameter){
        checkIsUserExist(userId);
        userRepository.editById(userId,updateParameter);
    }

    private void checkIsUserExist(Long userId){
        if (userRepository.findById(userId) == null) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다");
        }
    }

    public void deleteById(Long userId){
        checkIsUserExist(userId);
        userRepository.deleteById(userId);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

}
