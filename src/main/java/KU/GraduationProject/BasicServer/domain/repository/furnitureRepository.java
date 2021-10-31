package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface furnitureRepository extends JpaRepository<furniture,Long> {

    List<furniture> findAllByProject_ProjectId(@Param(value = "projectId") Long projectId);

}
