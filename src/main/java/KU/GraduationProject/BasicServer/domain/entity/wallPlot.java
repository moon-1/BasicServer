package KU.GraduationProject.BasicServer.domain.entity;

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
    @Column
    private Long floorPlanId;
}
