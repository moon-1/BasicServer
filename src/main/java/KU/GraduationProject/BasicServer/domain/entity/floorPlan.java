package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="floorPlanImage")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class floorPlan{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorPlanId;
    @Column
    private boolean isValid;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @Column
    private Long areaId;

}
