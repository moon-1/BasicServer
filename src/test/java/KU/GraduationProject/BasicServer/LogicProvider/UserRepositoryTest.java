package KU.GraduationProject.BasicServer.LogicProvider;

import KU.GraduationProject.BasicServer.DataModel.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    UserRepository userRepository = UserRepository.getInstance();

    @AfterEach
    void afterEach(){
        userRepository.ClearUser();
    }

    @Test
    void SavedUser(){

        //given
        User user = new User("user1","password1",20);

        //when
        User savedUser = userRepository.SaveUser(user);

        //then
        User findUser = userRepository.SearchUser(savedUser.getId());
        assertThat(findUser).isEqualTo(savedUser);
    }

    @Test
    void  SearchUser(){

        //given
        User user = new User("user1","password1",20);

        userRepository.SaveUser(user);

        //when
        User searchUser = userRepository.SearchUser(user.getId());

        //then
        List<User> users = userRepository.SearchAll();
        assertThat(users.contains(searchUser)).isTrue();
    }

    @Test
    void SearchAll() {

        //given
        User user1 = new User("user1","password1",20);
        User user2 = new User("user2","password2",20);

        userRepository.SaveUser(user1);
        userRepository.SaveUser(user2);

        //when
        List<User> users = userRepository.SearchAll();

        //then
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user1,user2);
    }

    @Test
    void UpdateUser(){

        //given
        User user = new User("user1","password1",20);
        User updatedData = new User("user2","password2",21);
        userRepository.SaveUser(user);

        //when
        userRepository.UpdateUser(user.getId(),updatedData);

        //then
        assertThat(user.getUserName()).isEqualTo(updatedData.getUserName());
        assertThat(user.getPassword()).isEqualTo(updatedData.getPassword());
        assertThat(user.getAge()).isEqualTo(updatedData.getAge());
    }

    @Test
    void DeleteUser(){

        //given
        User user = new User("user1","password1",20);

        userRepository.SaveUser(user);

        //when
        userRepository.DeleteUser(user.getId());
        List<User> users = userRepository.SearchAll();

        //then
        assertThat(users.contains(user)).isFalse();
    }
}
