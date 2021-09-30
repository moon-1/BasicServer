package KU.GraduationProject.BasicServer.domain.entity.floorPlans.doors;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot3D;
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
public class door {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doorId;
    @Column(nullable = true)
    private doorType doorType;
    @ManyToOne(targetEntity = wallPlot3D.class)
    @JoinColumn(name = "wallPlot3DId")
    private wallPlot3D wallPlot3D;
}
