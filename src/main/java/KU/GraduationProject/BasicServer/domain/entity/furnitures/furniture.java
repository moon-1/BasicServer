package KU.GraduationProject.BasicServer.domain.entity.furnitures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long furnitureId;
    @Column(nullable=false)
    private String name;
    @Column
    private String url;
    @Column(nullable = false)
    private String furniture3DData;

    public furniture(String name, String url, String furniture3DData){
        this.name = name;
        this.url = url;
        this.furniture3DData = furniture3DData;
    }
}
