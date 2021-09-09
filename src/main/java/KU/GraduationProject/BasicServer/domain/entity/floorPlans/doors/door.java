package KU.GraduationProject.BasicServer.domain.entity.floorPlans.doors;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "door")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class door {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doorId;
    @Column(nullable = true)
    private doorType doorType;
    @ManyToOne(targetEntity = wallPlot.class)
    @JoinColumn(name = "wallPlotId")
    private wallPlot wallPlot;
}
