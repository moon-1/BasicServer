package KU.GraduationProject.BasicServer.domain.entity.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class project {

    @JsonIgnore
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = user.class)
    @JoinColumn(name = "userId")
    private user user;

    @ManyToOne(targetEntity = imageFile.class)
    @JoinColumn(name = "imageFileId")
    private imageFile imageFile;

    public project(Date date, String name,user user,imageFile imageFile){
        this.date = date;
        this.name = name;
        this.user = user;
        this.imageFile = imageFile;
    }

}
