package nl.tudelft.sem.template.user;

import nl.tudelft.sem.template.user.domain.entities.AppUser;
import nl.tudelft.sem.template.user.domain.enums.Gender;
import nl.tudelft.sem.template.user.service.UserServiceImplementation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;

/**
 * App User microservice application.
 */
@SpringBootApplication
@ComponentScan({"nl.tudelft.sem.template.user.api", "nl.tudelft.sem.template.user.api.forms", "nl.tudelft.sem.template.user.authentication", "nl.tudelft.sem.template.user.service", "nl.tudelft.sem.template.user.config"})
@EntityScan({"nl.tudelft.sem.template.user.domain.entities", "nl.tudelft.sem.template.user.domain.enums"})
@EnableJpaRepositories("nl.tudelft.sem.template.user.repositories")
public class UserMicroService {
    public static void main(String[] args) {
        SpringApplication.run(UserMicroService.class, args);
    }

    @Bean
    CommandLineRunner run(UserServiceImplementation appUserService) {
        return args -> {
            //An example of adding data to the database
            appUserService.saveAppUser(new AppUser(null, Gender.MALE, "user1", "password1", new ArrayList<>(), new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, Gender.FEMALE, "user2", "password2", new ArrayList<>(), new ArrayList<>()));
        };
    }
}
