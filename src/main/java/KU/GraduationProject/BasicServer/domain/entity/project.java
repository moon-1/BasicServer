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
    @Column
    private Long userId;
    @Column
    private Long floorPlanId;

}
