package KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "wall")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class wall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallId;
    @Column
    private wallType wallType;
    @ManyToOne(targetEntity = wallPlot.class)
    @JoinColumn(name = "wallPlotId")
    private wallPlot wallPlot;

}
