package KU.GraduationProject.BasicServer.LogicProvider;

import KU.GraduationProject.BasicServer.DataModel.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private static final Map<Long, User> member = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    //singleton
    private static final UserRepository instance = new UserRepository();

    public static UserRepository getInstance() {
        return instance;
    }

    private UserRepository() {
    }

    public User SaveUser(User user){
        user.setId(++sequence);
        member.put(user.getId(),user);
        return user;
    }

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

