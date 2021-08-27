package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepositoryImpl extends JpaRepository<user,Long> {

//     Optional<user> findByUserName(String userName);

     Optional<user> findByEmail(String email);

//     boolean existsById(Long id);
}
