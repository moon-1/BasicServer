package KU.GraduationProject.BasicServer.Interface.Repository;

import KU.GraduationProject.BasicServer.Domain.user;

import java.util.List;

public interface userManageRepositoryImpl {

     user save(user user);
     List<user> findAll();
     user findById(Long id);
     user findByUserName(String userName);
     void editById(Long userId, user updateParameter);
     void deleteById(Long userId);
     void deleteAll();
}
