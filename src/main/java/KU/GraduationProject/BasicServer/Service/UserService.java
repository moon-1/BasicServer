package KU.GraduationProject.BasicServer.Service;

import KU.GraduationProject.BasicServer.Domain.User;
import KU.GraduationProject.BasicServer.Interface.Repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryImpl userRepository;

    public Long SaveUser(User user){
        CheckDuplicateUser(user);
        userRepository.SaveUser(user);
        return user.getId();
    }

    private void CheckDuplicateUser(User user){
        if (userRepository.SearchUser(user.getUserName()) != null) {
            throw new IllegalStateException("중복되는 아이디 입니다.");
        }
    }

    public List<User> SearchAll(){
        return userRepository.SearchAll();
    }

    public User SearchUser(Long id){
        return userRepository.SearchUser(id);
    }

    public User SearchUser(String userName){
        return userRepository.SearchUser(userName);
    }

    public void UpdateUser(Long userId, User updateParameter){
        CheckIsUserExist(userId);
        userRepository.UpdateUser(userId,updateParameter);
    }

    private void CheckIsUserExist(Long userId){
        if (userRepository.SearchUser(userId) == null) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다");
        }
    }

    public void DeleteUser(Long userId){
        CheckIsUserExist(userId);
        userRepository.DeleteUser(userId);
    }

    public void ClearUser(){
        userRepository.ClearUser();
    }

}
