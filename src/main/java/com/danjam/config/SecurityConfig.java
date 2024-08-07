package com.danjam.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((corsConfigurationSource) ->
                        corsConfigurationSource
                                .configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated())
                // formLogin 추가
                .formLogin(form ->
                                form
                                        .loginPage("/login")     // front에서 사용하는 로그인 페이지 url
//                                .usernameParameter("email")
//                                .passwordParameter("password")
                                        .loginProcessingUrl("/users/auth") // front에서 email+password 작성 받은 후 axios 전달하는 url, 실제로 login 진행됨
                                        .successForwardUrl("/users/authSuccess")
                                        .failureForwardUrl("/users/authFailure")
//                                .successHandler(new AuthenticationSuccessHandler() {
//                                    @Override
//                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                        System.out.println("Authentication success");
////                                        HashMap<String, Object> resultMap = new HashMap<>();
////                                        resultMap.put("result", "success"); // 이렇게 되면 result 값이 전달 되지 않음, 반드시 url 매핑해서 전송해야한다.
////                                        ResponseEntity.ok(resultMap);
//                                        response.sendRedirect("/users/authSuccess");
//                                    }
//                                })
//                                .failureHandler(new AuthenticationFailureHandler() {
//                                    @Override
//                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                                        System.out.println("Authentication failure : " + exception.getMessage());
//                                        System.out.println("Request URI : " + request.getRequestURI());
////                                        HashMap<String, Object> resultMap = new HashMap<>();
////                                        resultMap.put("result", "fail");
//                                        response.sendRedirect("/users/authFailure");
//                                    }
//                                })
                                        .permitAll()
                )
                .logout((logout) ->
                        logout
                                .logoutUrl("/user/logout")
//                                .logoutSuccessUrl("/user/logoutSuccess")
                                .logoutSuccessUrl("/")
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID"))
        ;

        return http.build();
    }

    //        addAllowedMethod 설정 같은거라 주석 남겨요
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 허용할 메소드 설정
//        configuration.addAllowedMethod("*");
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.addAllowedHeader("*"); // 필요에 따라 특정 헤더만 허용할 수 있습니다.
        configuration.setAllowCredentials(true); // 쿠키 및 자격 증명 허용 .cors(withDefaults())

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}