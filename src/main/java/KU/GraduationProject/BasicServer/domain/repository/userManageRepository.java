package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.user;
import KU.GraduationProject.BasicServer.Interface.Repository.userManageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Repository
//@RequiredArgsConstructor
public class userManageRepository{

//    private static final Map<Long, user> member = new ConcurrentHashMap<>();
//    private static long sequence = 0L;
//
//    public user save(user user){
//        user.setId(++sequence);
//        member.put(user.getId(),user);
//        return user;
//    }
//
//    public user findByUserName(String userName){ return member.get(userName);}
//
//    public user findById(Long id){
//        return member.get(id);
//    }
//
//    public List<user> findAll(){
//        return new ArrayList<>(member.values());
//    }
//
//    public void editById(Long userId, user updateParameter){
//        user user = findById(userId);
//        user.setUserName(updateParameter.getUserName());
//        user.setPassword(updateParameter.getPassword());
//        user.setAge(updateParameter.getAge());
//    }
//
//    public void deleteById(Long userId){
//        member.remove(userId);
//    }
//
//    public void deleteAll(){
//        member.clear();
//    }
}

