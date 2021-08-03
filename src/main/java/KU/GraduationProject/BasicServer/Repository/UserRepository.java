package KU.GraduationProject.BasicServer.Repository;

import KU.GraduationProject.BasicServer.Domain.User;
import KU.GraduationProject.BasicServer.Interface.Repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryImpl{

    private static final Map<Long, User> member = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public User SaveUser(User user){
        user.setId(++sequence);
        member.put(user.getId(),user);
        return user;
    }

    public User SearchUser(String userName){ return member.get(userName);}

    public User SearchUser(Long id){
        return member.get(id);
    }

    public List<User> SearchAll(){
        return new ArrayList<>(member.values());
    }

    public void UpdateUser(Long userId, User updateParameter){
        User user = SearchUser(userId);
        user.setUserName(updateParameter.getUserName());
        user.setPassword(updateParameter.getPassword());
        user.setAge(updateParameter.getAge());
    }

    public void DeleteUser(Long userId){
        member.remove(userId);
    }

    public void ClearUser(){
        member.clear();
    }
}

