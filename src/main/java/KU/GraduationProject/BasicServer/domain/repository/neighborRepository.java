package KU.GraduationProject.BasicServer.domain.repository;

import KU.GraduationProject.BasicServer.domain.entity.account.neighbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface neighborRepository extends JpaRepository<neighbor,Long> {

    List<neighbor> findAllByUser_UserId(@Param(value = "userId") Long userId);

    @Modifying
    @Query("update neighbor set isApprove= :isApprove where neighborId =:neighborId")
    void setNeighborIsApprove(@Param("neighborId") Long neighborId, @Param("isApprove") boolean isApprove);


}
