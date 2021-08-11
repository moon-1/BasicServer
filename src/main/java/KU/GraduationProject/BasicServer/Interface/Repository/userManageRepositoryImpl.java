package KU.GraduationProject.BasicServer.Interface.Repository;

import KU.GraduationProject.BasicServer.domain.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface userManageRepositoryImpl extends JpaRepository<user,Long> {

     Optional<user> findByUserName(String userName);
}
