package KU.GraduationProject.BasicServer.domain.entity.furnitures;

import KU.GraduationProject.BasicServer.domain.entity.project.project;
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
public class furniture3D {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long furniture3DId;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture.class)
    @JoinColumn(name = "furnitureId")
    private furniture furniture;
    @ManyToOne(targetEntity = KU.GraduationProject.BasicServer.domain.entity.project.project.class)
    @JoinColumn(name = "projectId")
    private project project;

    public furniture3D(furniture furniture, project project)
    {
        this.furniture = furniture;
        this.project = project;
    }
}
