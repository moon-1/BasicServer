package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class interiorWall {

    public interiorWall(wall wall, double thickness) {
        this.wall = wall;
        this.thickness = thickness;
    }

    public wall wall;

    public double thickness;
}
