package KU.GraduationProject.BasicServer.LogicProvider;

import KU.GraduationProject.BasicServer.DataModel.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    //테스트할 클래스를 싱글톤으로 가져옴
    UserRepository userRepository = UserRepository.getInstance();

    //test코드의 경우 실행 순서가 불분명함. 따라서 save()이후 findAll()실행될 때 save에서
    //member변수에 저장한 값이 그대로 findAll()에 포함되어 테스트 fail이 날 수도 있음
    //따라서 각 @Test가 종료될때마다 실행되는 @AfterEach를 통해 memberRepository를 초기화해주는
    //과정 반드시 포함
    @AfterEach
    void afterEach() {
        userRepository.ClearUser();
    }

    //회원 저장 함수의 테스트 코드
    @Test
    void SavedUser() {

        //given
        User user = new User("user1","password1",20);

        //when
        //~~조건의 경우에
        User savedUser = userRepository.SaveUser(user);

        //then
        //결과값이 옳은지 검증
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
