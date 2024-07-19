package org.gbbv.musikkspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        {
                            try {
                                authorize.requestMatchers("/register/**").permitAll()
                                        .requestMatchers("/index").permitAll()
                                        .requestMatchers("genre/add").hasRole("ADMIN")
                                        .requestMatchers("/album/add").hasRole("ADMIN")
                                        .requestMatchers("/author/add").hasRole("ADMIN")
                                        .requestMatchers("/album/delete/**").hasRole("ADMIN")
                                        .requestMatchers("/author/delete/**").hasRole("ADMIN")
                                        .requestMatchers("/genre/delete/**").hasRole("ADMIN")
                                        .requestMatchers("/album/edit/**").hasRole("ADMIN")
                                        .requestMatchers("/author/edit/**").hasRole("ADMIN")
                                        .requestMatchers("/genre/edit/**").hasRole("ADMIN")
                                        .requestMatchers("/cart/**").authenticated()
                                        .requestMatchers("/order/**").authenticated()
                                        .requestMatchers("/users/**").hasRole("ADMIN")
                                        .requestMatchers("/user/**").authenticated()
                                        .requestMatchers("/album/delete").hasRole("ADMIN")
                                        .requestMatchers("/author/delete").hasRole("ADMIN")
                                        .requestMatchers("/genre/delete").hasRole("ADMIN")
                                        .requestMatchers("/album/edit").hasRole("ADMIN")
                                        .requestMatchers("/author/edit").hasRole("ADMIN")
                                        .requestMatchers("/genre/edit").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                                        .and().exceptionHandling().accessDeniedPage("/error-403");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}