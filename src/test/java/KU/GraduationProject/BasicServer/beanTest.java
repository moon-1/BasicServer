package KU.GraduationProject.BasicServer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class beanTest {
    @Autowired
    DefaultListableBeanFactory beanFactory;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void CheckBean(){
        log.info("------------------------------------------");
        for(String n : beanFactory.getBeanDefinitionNames()){
            log.info(n + "\t" + beanFactory.getBean(n).getClass().getName());
        }
        log.info("------------------------------------------");
    }
}
