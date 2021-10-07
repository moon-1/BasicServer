package KU.GraduationProject.BasicServer.dto.imageProcessingData;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.pointType;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class pointDto{

    private double x;

    private double y;

    private pointType pointType;

    public pointDto(double x, double y, pointType pointType){
        this.x = x;
        this.y = y;
        this.pointType = pointType;
    }
}
