package KU.GraduationProject.BasicServer.domain.entity.project;

import KU.GraduationProject.BasicServer.domain.entity.account.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class imageFile {

    @Id
    @GeneratedValue
    @Column
    private Long imageFileId;

    @Column
    private String fileName;

    @Column
    private String fileDownloadUri;

    @Column
    private String fileType;

    @Column
    private Long size;

    @ManyToOne(targetEntity = user.class)
    @JoinColumn(name = "userId")
    private user user;

}
