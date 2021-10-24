package KU.GraduationProject.BasicServer.dto.imageProcessingDto;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.pointType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class pointDto{

    private double x;

    private double y;

    @JsonIgnore
    private pointType pointType;

    public pointDto(double x, double y, pointType pointType){
        this.x = x;
        this.y = y;
        this.pointType = pointType;
    }
}
