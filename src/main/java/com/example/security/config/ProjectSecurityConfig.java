package com.example.security.config;

import com.example.security.exceptionHandling.CustomAccessDeniedHandler;
import com.example.security.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) //برای https را فعال کردن
                .authorizeHttpRequests((request) -> request


                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/notices",
                                "/contact",
                                "/invalid-session"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/create").permitAll()
                        .requestMatchers("/my").denyAll()
                        .anyRequest().authenticated())
                .sessionManagement(smc -> smc.sessionFixation().newSession()

                        .invalidSessionUrl("/invalid-session").maximumSessions(1)
                        .maxSessionsPreventsLogin(true)//محدودیت تعداد لاگین

                )
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/user/create","v3/api-docs/**", "/swagger-ui/**"));
                .csrf(csrf -> csrf.disable())
                .cors(cors->cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:5173/"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"))  ;
                        config.setAllowCredentials(true);
                        config.setMaxAge(3600L);

                        return config;
                    }
                }));
        http.httpBasic(hbb -> hbb.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(ehcCustomAccessDeniedHandler())
                //  .accessDeniedPage("/access-denied")
        );

        http.formLogin(withDefaults());
        return http.build();
    }


//    @Bean
//    public CustomBasicAuthenticationEntryPoint hbbCustomAuthenticationEntryPoint() {
//        return new CustomBasicAuthenticationEntryPoint();
//    }

    @Bean
    public CustomAccessDeniedHandler ehcCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
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
