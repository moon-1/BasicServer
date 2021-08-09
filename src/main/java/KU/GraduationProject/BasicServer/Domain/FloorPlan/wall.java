package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class wall {

    public wall(wallPoint startPoint, wallPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public wallPoint startPoint;

    public wallPoint endPoint;
}
