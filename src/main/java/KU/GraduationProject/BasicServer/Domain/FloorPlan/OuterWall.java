package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class OuterWall {

    public OuterWall(Wall wall, double thickness) {
        this.wall = wall;
        this.thickness = thickness;
    }

    public Wall wall;

    public double thickness;
}
