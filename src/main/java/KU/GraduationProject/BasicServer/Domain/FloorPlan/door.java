package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class door {

    public door(wall wall,doorProperty doorProperty) {
        this.wall = wall;
        this.doorProperty = doorProperty;
    }

    public wall wall;

    public doorProperty doorProperty;
}
