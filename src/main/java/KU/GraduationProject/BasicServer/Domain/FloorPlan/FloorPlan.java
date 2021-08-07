package KU.GraduationProject.BasicServer.Domain.FloorPlan;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class FloorPlan {

    public FloorPlan(){ }

    public FloorPlan(Long id, String city,String local, String area, String floorPlanName ){
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

    private List<OuterWall> outerWall;

    private List<InteriorWall> interiorWall;

    private List<Door> door;

}