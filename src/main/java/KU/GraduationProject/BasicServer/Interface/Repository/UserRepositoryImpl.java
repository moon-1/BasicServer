package KU.GraduationProject.BasicServer.Interface.Repository;

import KU.GraduationProject.BasicServer.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepositoryImpl{

     User SaveUser(User user);
     List<User> SearchAll();
     User SearchUser(Long id);
     User SearchUser(String userName);
     void UpdateUser(Long userId, User updateParameter);
     void DeleteUser(Long userId);
     void ClearUser();
}
