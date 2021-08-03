package KU.GraduationProject.BasicServer.Config;

import KU.GraduationProject.BasicServer.Interface.Repository.UserRepositoryImpl;
import KU.GraduationProject.BasicServer.Repository.UserRepository;
import KU.GraduationProject.BasicServer.Service.UserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class BeanConfig {

    public FilterRegistrationBean HiddenHttpMethodFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }

}
