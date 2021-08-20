package KU.GraduationProject.BasicServer.domain.entity.floorPlans;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.floorPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "wallPlot")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class wallPlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallPlotId;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.floorPlans.floorPlan.class)
    @JoinColumn(name = "floorPlanId")
    private floorPlan floorPlan;
}
