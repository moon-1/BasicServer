package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepositoryImpl extends JpaRepository<user,Long> {

     Optional<user> findByUserName(String userName);

     boolean existsById(Long id);
}
