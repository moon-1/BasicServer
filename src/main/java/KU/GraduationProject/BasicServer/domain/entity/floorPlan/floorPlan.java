package KU.GraduationProject.BasicServer.domain.entity.floorPlan;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class floorPlan {

    public floorPlan(){ }

    public floorPlan(Long id, String city, String local, String area, String floorPlanName ){
        this.id = id;
        this.city = city;
        this.local = local;
        this.area = area;
        this.floorPlanName = floorPlanName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;

    private String local;

    private String area;

    private String floorPlanName;

    private List<outerWall> outerWall;

    private List<interiorWall> interiorWall;

    private List<door> door;

}