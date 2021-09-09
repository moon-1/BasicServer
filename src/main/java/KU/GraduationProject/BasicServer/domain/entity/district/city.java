package KU.GraduationProject.BasicServer.domain.entity.district;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="city")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class city {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    @Column(name = "name")
    private String name;

    public city(String name){
        this.name = name;
    }
}
