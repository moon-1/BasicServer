package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="area")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long areaId;
    @Column
    private String name;
    @Column
    private Long cityId;

    public area(String name,Long cityId){
        this.name = name;
        this.cityId = cityId;
    }
}
