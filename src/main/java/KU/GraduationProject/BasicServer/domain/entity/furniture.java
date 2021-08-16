package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name ="furniture")
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
}
