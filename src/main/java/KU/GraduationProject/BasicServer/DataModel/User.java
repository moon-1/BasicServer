package KU.GraduationProject.BasicServer.DataModel;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String userName;
    private String password;
    private Integer age;

    public User(){ }

    public User(String userName, String password, Integer age){
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}
