package com.web.seo.config;

import com.web.seo.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize ->
                        authorize
                                .requestMatchers("/hello").hasAuthority("ROLE_USER")
                                .requestMatchers("/", "/home", "signup/**").permitAll()  // Halaman yang dapat diakses tanpa otentikasi
                                .anyRequest().authenticated()  // Semua permintaan lainnya memerlukan otentikasi
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .permitAll()  // Izinkan akses ke halaman login tanpa otentikasi
                )
                .logout(logout ->
                        logout.permitAll()  // Izinkan akses ke logout tanpa otentikasi
                );

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("adminpassword")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(admin);
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
