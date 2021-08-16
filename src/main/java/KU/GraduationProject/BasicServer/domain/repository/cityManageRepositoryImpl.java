package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.city;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface cityManageRepositoryImpl extends JpaRepository<city,Long> {

    Optional<city> findByName(String name);

    boolean existsByName(String name);


}
