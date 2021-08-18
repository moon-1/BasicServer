package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="floorPlan")
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
    @ManyToOne(targetEntity = area.class)
    @JoinColumn(name = "areaId")
    private area area;

    public floorPlan(boolean isValid,byte[] image,area area){
        this.isValid = isValid;
        this.image = image;
        this.area = area;

    }

}
