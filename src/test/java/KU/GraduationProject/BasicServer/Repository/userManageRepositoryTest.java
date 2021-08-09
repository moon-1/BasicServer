package KU.GraduationProject.BasicServer.Repository;

import KU.GraduationProject.BasicServer.Domain.user;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class userManageRepositoryTest {

    userManageRepository userRepository = new userManageRepository();

    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    void save(){

        //given
        user user = new user("user1","password1",20);

        //when
        user savedUser = userRepository.save(user);

        //then
        user findUser = userRepository.findById(savedUser.getId());
        assertThat(findUser).isEqualTo(savedUser);
    }

    @Test
    void  findById(){

        //given
        user user = new user("user1","password1",20);

        userRepository.save(user);

        //when
        user searchUser = userRepository.findById(user.getId());

        //then
        List<user> users = userRepository.findAll();
        assertThat(users.contains(searchUser)).isTrue();
    }

    @Test
    void findAll() {

        //given
        user user1 = new user("user1","password1",20);
        user user2 = new user("user2","password2",20);

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        List<user> users = userRepository.findAll();

        //then
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1,user2);
    }

    @Test
    void editById(){

        //given
        user user = new user("user1","password1",20);
        user updatedData = new user("user2","password2",21);
        userRepository.save(user);

        //when
        userRepository.editById(user.getId(),updatedData);

        //then
        assertThat(user.getUserName()).isEqualTo(updatedData.getUserName());
        assertThat(user.getPassword()).isEqualTo(updatedData.getPassword());
        assertThat(user.getAge()).isEqualTo(updatedData.getAge());
    }

    @Test
    void deleteById(){

        //given
        user user = new user("user1","password1",20);

        userRepository.save(user);

        //when
        userRepository.deleteById(user.getId());
        List<user> users = userRepository.findAll();

        //then
        assertThat(users.contains(user)).isFalse();
    }
}
