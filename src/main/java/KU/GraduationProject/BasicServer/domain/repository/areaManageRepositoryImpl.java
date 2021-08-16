package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface areaManageRepositoryImpl extends JpaRepository<area,Long> {

    Optional<area> findByName(String name);

    List<area> findByCityId(Long cityId);
}