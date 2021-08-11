package KU.GraduationProject.BasicServer.domain.entity.floorPlan;

public class door {

    public door(wall wall,doorProperty doorProperty) {
        this.wall = wall;
        this.doorProperty = doorProperty;
    }

    public wall wall;

    public doorProperty doorProperty;
}
