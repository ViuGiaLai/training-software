package com.viusoftware.training.training_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import com.viusoftware.training.training_system.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Cho phép cả http và https
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:8080",
            "https://training-software.onrender.com",
            "http://training-software.onrender.com"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors() // bật CORS
            .and()
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/**")
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true) // Đổi thành true: không cho phép đăng nhập mới khi đã có session
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/training-login", "/css/**", "/images/**", "/js/**", "/error").permitAll()
                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER") // Sửa lại: cho phép cả ADMIN và MANAGER
                .requestMatchers("/teacher/**").hasRole("TEACHER")
                .requestMatchers("/gv/**").hasRole("TEACHER")
                .requestMatchers("/sv/**").hasRole("STUDENT")
                .requestMatchers("/admin/dashboard/classroom/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/training-login")
                .loginProcessingUrl("/training-login")
                .successHandler(customSuccessHandler())
                .failureHandler(customFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/training-logout")
                .logoutSuccessUrl("/training-login?logout")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .orElse("");

            logger.info("User '{}' logged in successfully with role '{}'.", authentication.getName(), role);

            switch (role) {
                case "ADMIN":
                    response.sendRedirect("/admin/dashboard");
                    break;
                case "TEACHER":
                    response.sendRedirect("/gv/teacher");
                    break;
                case "STUDENT":
                    response.sendRedirect("/sv/student");
                    break;
                default:
                    response.sendRedirect("/default-dashboard");
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
                String errorMessage = "Tên đăng nhập hoặc mật khẩu không chính xác!";

                if (exception instanceof LockedException) {
                    errorMessage = "Tài khoản của bạn đã bị khóa.";
                } else if (exception instanceof DisabledException) {
                    errorMessage = "Tài khoản của bạn đã bị vô hiệu hóa.";
                } else if (exception instanceof BadCredentialsException) {
                    errorMessage = "Tên đăng nhập hoặc mật khẩu không chính xác!";
                }

                String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                getRedirectStrategy().sendRedirect(request, response, "/training-login?error=true&message=" + encodedMessage);
            }
        };
    }
}