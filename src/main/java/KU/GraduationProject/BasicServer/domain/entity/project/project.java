package KU.GraduationProject.BasicServer.domain.entity.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.floorPlan;
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
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String name;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.account.user.class)
    @JoinColumn(name = "userId")
    private user user;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.floorPlans.floorPlan.class)
    @JoinColumn(name = "floorPlanId")
    private floorPlan floorPlan;

    public project(Date date, String name, user user, floorPlan floorPlan){
        this.date = date;
        this.name = name;
        this.user = user;
        this.floorPlan = floorPlan;
    }

}
