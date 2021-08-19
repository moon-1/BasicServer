package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name ="project")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    @Column
    private Date date;
    @Column
    private String name;
    @ManyToOne(targetEntity = user.class)
    @JoinColumn(name = "userId")
    private user user;
    @ManyToOne(targetEntity = floorPlan.class)
    @JoinColumn(name = "floorPlanId")
    private floorPlan floorPlan;

    public project(Date date, String name, user user, floorPlan floorPlan){
        this.date = date;
        this.name = name;
        this.user = user;
        this.floorPlan = floorPlan;
    }

}
