package KU.GraduationProject.BasicServer.domain.entity.floorPlans;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.doors.door;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "point")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;
    @Column
    private double x;
    @Column
    private double y;
    @Column
    private double thickness;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.floorPlans.doors.door.class)
    @JoinColumn(name = "doorId", nullable = true)
    private door door;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall.class)
    @JoinColumn(name = "wallId", nullable = true)
    private wall wall;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.floorPlans.window.class)
    @JoinColumn(name = "windowId", nullable = true)
    private window window;


}
