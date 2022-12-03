package nl.tudelft.sem.template.user;

import nl.tudelft.sem.template.user.domain.entities.AppUser;
import nl.tudelft.sem.template.user.domain.entities.Role;
import nl.tudelft.sem.template.user.domain.enums.Gender;
import nl.tudelft.sem.template.user.domain.enums.RoleType;
import nl.tudelft.sem.template.user.service.AppUserService;
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
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService appUserService) {
        return args -> {
            //An example of adding data to the database
            appUserService.saveAppUser(new AppUser(null, Gender.MALE, "user1", "password1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, Gender.FEMALE, "user2", "password2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

            appUserService.saveRole(new Role(null, RoleType.ROWER));

            appUserService.addRoleToAppUser(1L, 3L);
        };
    }
}
