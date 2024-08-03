//package com.danjam.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer :: disable)
//                .cors((corsConfigurationSource)->
//                        corsConfigurationSource
//                                .configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests((authorize) ->
//                        authorize
//                                .requestMatchers("/**").permitAll()
//                                .anyRequest().authenticated())
//                .logout((logout) ->
//                        logout
//                                .logoutUrl("/user/logout")
//                                .logoutSuccessUrl("/user/logoutSuccess")
//                                .clearAuthentication(true)
//                                .deleteCookies("JSESSIONID"))
//        ;
//
//        return http.build();
//    }
//
////        addAllowedMethod 설정 같은거라 주석 남겨요
////        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 허용할 메소드 설정
////        configuration.addAllowedMethod("*");
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        configuration.addAllowedHeader("*"); // 필요에 따라 특정 헤더만 허용할 수 있습니다.
//        configuration.setAllowCredentials(true); // 쿠키 및 자격 증명 허용 .cors(withDefaults())
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//}