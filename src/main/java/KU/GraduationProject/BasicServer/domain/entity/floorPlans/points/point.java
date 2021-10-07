package KU.GraduationProject.BasicServer.domain.entity.floorPlans.points;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall;
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
public class point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private pointType pointType;

    @ManyToOne(targetEntity = wall.class)
    @JoinColumn(name = "wallId", nullable = true)
    private wall wall;

    public point(double x,double y, pointType pointType, wall wall){
        this.x = x;
        this.y = y;
        this.pointType = pointType;
        this.wall = wall;
    }

}
