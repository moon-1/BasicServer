package KU.GraduationProject.BasicServer.Domain.FloorPlan;

public class Door {

    public Door(Wall wall, DoorProperty doorProperty) {
        this.wall = wall;
        this.doorProperty = doorProperty;
    }

    public Wall wall;

    public DoorProperty doorProperty;
}
