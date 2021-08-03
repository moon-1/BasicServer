package KU.GraduationProject.BasicServer;

import KU.GraduationProject.BasicServer.Domain.User;
import KU.GraduationProject.BasicServer.Repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
//@ComponentScan("KU.GraduationProject.BasicServer.Controller")
//@EntityScan(basePackageClasses = User.class)
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BasicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicServerApplication.class, args);
	}
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}
}
