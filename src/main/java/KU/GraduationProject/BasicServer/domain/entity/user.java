package KU.GraduationProject.BasicServer.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @Temporal(TemporalType.DATE)
    @Column
    private Date birth;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public user(String userName,String password,String email,Date birth,byte[] image){
        this.userName= userName;
        this.password = password;
        this.email = email;
        this.birth = birth;
        this.image = image;
    }
}
