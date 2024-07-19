package org.gbbv.musikkspring.config;

import org.gbbv.musikkspring.model.Role;
import org.gbbv.musikkspring.model.RoleEnum;
import org.gbbv.musikkspring.model.User;
import org.gbbv.musikkspring.repository.RoleRepository;
import org.gbbv.musikkspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class StartupConfig {
    @Bean
    public ApplicationRunner initializer(RoleRepository roleRepository) {
        return args -> {
            String[] roles = {RoleEnum.USER.name(), RoleEnum.ROLE_ADMIN.name(), RoleEnum.GIGA_CHAD.name()};
            for (String roleName : roles) {
                if (!roleRepository.existsByName(roleName)) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
            }
        };
    }
}