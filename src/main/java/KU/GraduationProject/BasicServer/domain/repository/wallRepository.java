package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface wallRepository extends JpaRepository<wall,Long> {

    List<wall> findAllByContour_ContourId(@Param(value = "contourId") Long contourId);

}
