package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class Wall {

    public Wall(WallPoint startPoint, WallPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public WallPoint startPoint;

    public WallPoint endPoint;
}
