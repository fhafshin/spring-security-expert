package com.example.security.config;

import com.example.security.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //.requiresChannel(rcc->rcc.anyRequest().requiresSecure()) برای https را فعال کردن
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/notices",
                                "/contact"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/create").permitAll()
                        .anyRequest().authenticated())
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/user/create","v3/api-docs/**", "/swagger-ui/**"));
                        .csrf(csrf->csrf.disable());
        http.httpBasic(hbb->hbb.authenticationEntryPoint(hbbCustomAuthenticationEntryPoint()));
        http.formLogin(withDefaults());
        return http.build();
    }


    @Bean
    public CustomBasicAuthenticationEntryPoint hbbCustomAuthenticationEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }


    //بارگزاری از روی حافظه
//    @Bean
//    UserDetailsService inMemoryUserDetails() {
//        UserDetails user = User.builder()
//                .username("user").password(passwordEncoder().encode("123"))
//                .authorities("read").build();
//
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("123"))
//                .authorities("admin").build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }



    //اجرای پیش فرض لاگین
    //لازم است ابتدا دو جدول مطابق باا جداول پیشنهادی ساخته شود تا کار کند همچنین لازم است پسورد انکدر نیز از نوع عمومی باشد
//    @Bean
//    UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
       // return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        
        return new CustomEncodePassword();
    }

//استعلام از api پسورد های ساده
//    @Bean
//    CompromisedPasswordChecker checker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

}
