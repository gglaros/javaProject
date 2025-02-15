package hua.project.config;

import hua.project.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// orizoyme to config kai to web secutiry

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private UserService userService;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;


    public SecurityConfig(UserService userService, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home","register","saveUser", "/images/**", "/js/**", "/css/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers("/OwnerApplications","/OwnerApplications/change/appStatus/{appId}","owner","owner/show/properties/{ownerId}","property/**",
                                "tenant","tenant/all/applications","tenant/change/valStatus/{tenantId}","/users","/user/delete/{userId}").hasRole("ADMIN")

                        .requestMatchers("/OwnerApplications/make/{ownerId}","owner/make/property/{id}","owner/OwnerApplications","owner/show/properties", "owner/show/requests",
                                "tenantApplications/change/appStatus/{appId}").hasRole("OWNER")

                        .requestMatchers("/tenantApplications/viewProperties","tenant/rentalRequests","tenant/profile").hasRole("TENANT")
                        .anyRequest().authenticated()
                )


                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }


}
