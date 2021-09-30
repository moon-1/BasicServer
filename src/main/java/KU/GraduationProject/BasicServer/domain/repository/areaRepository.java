package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.district.area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface areaRepository extends JpaRepository<area,Long> {

    Optional<area> findByName(String name);

    boolean existsByName(String name);

    List<area> findByCity_cityId(@Param(value="cityId")Long cityId);

}
