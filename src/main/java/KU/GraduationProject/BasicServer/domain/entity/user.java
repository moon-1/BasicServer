package KU.GraduationProject.BasicServer.domain.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity(name="MEMBER")
@AllArgsConstructor
@Builder
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String userName;
    @Column
    private String password;
    @Column
    private Integer age;

    public user(){ }

    public user(String userName, String password, Integer age){
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}
