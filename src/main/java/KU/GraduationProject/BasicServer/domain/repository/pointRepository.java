package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.contour;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface pointRepository extends JpaRepository<point,Long> {

    List<point> findAllByWall_WallId(@Param(value = "wallId") Long wallId);

}
