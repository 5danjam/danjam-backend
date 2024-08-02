//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserDetailsServiceImpl userDetailsService) throws Exception {
//    httpSecurity
//            .csrf(AbstractHttpConfigurer :: disable)
//            .authorizeHttpRequests((authorize) ->
//                    authorize
//                            .requestMatchers("/user/**").permitAll()
//                            .anyRequest().authenticated())
//            .formLogin((form) ->
//                    form
//                            .loginPage("/login")
//                            .loginProcessingUrl("/user/auth")
//                            .successForwardUrl("/user/authSuccess")
//                            .failureForwardUrl("/user/authFail"))
//
//            .logout((logout) ->
//                    logout
//                            .logoutUrl("/user/logout")
//                            .logoutSuccessUrl("/user/logoutSuccess")
//                            .clearAuthentication(true)
//                            .deleteCookies("JSESSIONID"))
//            .userDetailsService(userDetailsService);
//
//    httpSecurity.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    return httpSecurity.build();
//}
//
//@Bean
//public CorsFilter corsFilter() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowCredentials(true);
//    configuration.addAllowedOrigin("http://localhost:3000");//해당요청은 허락해준다.
//    configuration.addAllowedHeader("*");
//    configuration.addAllowedMethod("*");
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//
//    return new CorsFilter(source);
//}