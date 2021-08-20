package KU.GraduationProject.BasicServer.domain.entity.floorPlans;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "window")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class window {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long windowId;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot.class)
    @JoinColumn(name = "wallPlotId")
    private wallPlot wallPlot;
}
