package KU.GraduationProject.BasicServer.domain.entity.floorPlan;

public class interiorWall {

    public interiorWall(wall wall, double thickness) {
        this.wall = wall;
        this.thickness = thickness;
    }

    public wall wall;

    public double thickness;
}
