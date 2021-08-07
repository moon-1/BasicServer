package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class InteriorWall {

    public InteriorWall(Wall wall, double thickness) {
        this.wall = wall;
        this.thickness = thickness;
    }

    public Wall wall;

    public double thickness;
}
