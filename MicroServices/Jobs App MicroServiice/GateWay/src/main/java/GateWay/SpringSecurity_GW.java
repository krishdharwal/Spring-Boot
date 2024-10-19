//package GateWay;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurity_GW {
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeHttpRequests(request -> request
//                        .requestMatchers("/public/**").permitAll()
//                        .requestMatchers("/journal" +
//                                "*").hasRole("ADMIN")
//                        .anyRequest().permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
//        return auth.getAuthenticationManager();
//    }
//
//}