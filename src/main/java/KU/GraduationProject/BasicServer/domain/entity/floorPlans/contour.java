package KU.GraduationProject.BasicServer.domain.entity.floorPlans;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wallType;
import lombok.*;
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

    @ManyToOne(targetEntity = wallPlot3D.class)
    @JoinColumn(name = "wallPlot3DId")
    private wallPlot3D wallPlot3D;

    public contour(wallPlot3D wallPlot3D){
        this.wallPlot3D = wallPlot3D;
    }
}
