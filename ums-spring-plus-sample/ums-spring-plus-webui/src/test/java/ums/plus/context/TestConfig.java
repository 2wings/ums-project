package ums.plus.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ums.plus.service.IUserService;
import ums.plus.service.UserService;

@Configuration
@ComponentScan(basePackages = { "ums.plus.controller", "ums.plus.repository", "ums.plus.domain", "ums.plus.service" })
public class TestConfig {

    @Bean
    public IUserService getUserService() {
        return new UserService();
    }

}
