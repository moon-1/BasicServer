package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.project.project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface projectRepository extends JpaRepository<project,Long> {

    List<project> findAllByUser_UserId(@Param(value = "userId") Long userId);
}
