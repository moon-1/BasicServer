package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface uploadImageFileInfoRepository extends JpaRepository<imageFile,Long> {

    List<imageFile> findAllByImageFileId(Long id);

    Optional<imageFile> findByFileName(String fileName);
}
