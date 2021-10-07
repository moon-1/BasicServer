package KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.contour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class wall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallId;

    @Column
    @Nullable
    @Enumerated(EnumType.STRING)
    private wallType wallType;

    @ManyToOne(targetEntity = contour.class)
    @JoinColumn(name = "contourId")
    private contour contour;

    public wall(contour contour){
        this.contour = contour;
    }

    public wall(wallType wallType,contour contour){
        this.wallType = wallType;
        this.contour = contour;
    }



}
