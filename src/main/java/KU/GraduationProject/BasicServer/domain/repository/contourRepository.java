package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.contour;
import KU.GraduationProject.BasicServer.domain.entity.project.project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface contourRepository extends JpaRepository<contour,Long> {

    List<contour> findAllByImageFile_ImageFileId(@Param(value = "imageFileId") Long imageFileId);
}
