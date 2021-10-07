package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot3D;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface wallPlot3DRepository extends JpaRepository<wallPlot3D,Long> {

}
