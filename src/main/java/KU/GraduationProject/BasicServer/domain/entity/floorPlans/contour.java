package KU.GraduationProject.BasicServer.domain.entity.floorPlans;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wallType;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class contour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contourId;

    @ManyToOne(targetEntity = imageFile.class)
    @JoinColumn(name = "imageFileId", nullable = false)
    private imageFile imageFile;

    public contour(imageFile imageFile){
        this.imageFile = imageFile;
    }

}
