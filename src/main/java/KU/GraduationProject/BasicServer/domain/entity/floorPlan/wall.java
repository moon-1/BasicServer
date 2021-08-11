package KU.GraduationProject.BasicServer.domain.entity.floorPlan;

public class wall {

    public wall(wallPoint startPoint, wallPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public wallPoint startPoint;

    public wallPoint endPoint;
}
