package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name="furniture3D")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class furniture3D {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long furniture3DId;
    @Column
    private Long furnitureId;
    @Column
    private Long projectId;
}
