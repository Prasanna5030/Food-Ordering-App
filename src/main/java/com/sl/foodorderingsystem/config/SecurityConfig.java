package com.sl.foodorderingsystem.config;

import com.sl.foodorderingsystem.JWT.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;

import static com.sl.foodorderingsystem.entity.Permission.*;
import static com.sl.foodorderingsystem.entity.Role.ADMIN;
import static com.sl.foodorderingsystem.entity.Role.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/auth/**")
                                .permitAll()
                                .requestMatchers("/home/**").hasAnyRole(ADMIN.name(), USER.name())
                                .requestMatchers(GET, "/home/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                .requestMatchers(POST, "/home/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    public Boolean isAdmin(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
        return authorities.stream().anyMatch(authority-> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer(){
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:4200")
                        .allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name(), PATCH.name())
                        .allowedHeaders(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE,HttpHeaders.ACCEPT,HttpHeaders.ACCEPT_LANGUAGE,HttpHeaders.ACCEPT_ENCODING)
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

}
