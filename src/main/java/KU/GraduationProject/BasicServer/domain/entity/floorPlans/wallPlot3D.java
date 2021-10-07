package KU.GraduationProject.BasicServer.domain.entity.floorPlans;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class wallPlot3D {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallPlot3DId;

    @ManyToOne(targetEntity = imageFile.class)
    @JoinColumn(name = "imageFileId", nullable = false)
    private imageFile imageFile;

    public wallPlot3D(imageFile imageFile){
        this.imageFile = imageFile;
    }
}
