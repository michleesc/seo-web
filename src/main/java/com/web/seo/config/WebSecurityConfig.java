package com.web.seo.config;

import com.web.seo.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

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
                .csrf(csrf -> csrf.disable())
//                .requiresChannel(channel ->
//                        channel.anyRequest().requiresSecure()) https request
                .authorizeRequests(authorize -> authorize
                                .antMatchers("/**").permitAll()
//                        .antMatchers("/seo/**", "/templates/**").permitAll() // Allow static resources
//                        .antMatchers("/", "/home", "/signup/**", "/content/**").permitAll() // Allow public endpoints
//                        .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v2/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
//                        .anyRequest().authenticated() // Protect other endpoints
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

    // tanpa menggunakan database login
//    @Bean
//    public UserDetailsService userDetailsService() throws Exception {
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("adminpassword")
//                        .roles("ADMIN")
//                        .build();
//        return new InMemoryUserDetailsManager(admin);
//    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
