package KU.GraduationProject.BasicServer.domain.entity.furnitures;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
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
public class furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long furnitureId;

    @Column
    private String name;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @ManyToOne(targetEntity = project.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "projectId")
    private project project;

    public furniture(String name,double x,double y,project project){
        this.name = name;
        this.x = x;
        this.y = y;
        this.project = project;
    }

}
