package KU.GraduationProject.BasicServer.Domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
//@Entity
//@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50, nullable = false)
    private String userName;
    @Column
    private String password;
    @Column
    private Integer age;

    public User(){ }

    public User(String userName, String password, Integer age){
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}
