package KU.GraduationProject.BasicServer.domain.entity.district;

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
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.district.city.class)
    @JoinColumn(name = "cityId")
    private city city;

    public area(String name, city city){
        this.name = name;
        this.city = city;
    }
}
