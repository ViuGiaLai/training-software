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
import com.viusoftware.training.training_system.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/**")
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/tranning-login", "/css/**", "/images/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/tranning-login")
                .loginProcessingUrl("/tranning-login")
                .successHandler(customSuccessHandler())
                .failureHandler(customFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/tranning-logout")
                .logoutSuccessUrl("/tranning-login?logout")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    logger.warn("Unauthorized access attempt to {}", request.getRequestURI());
                    String message = "Bạn cần đăng nhập để truy cập trang này";
                    String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
                    response.sendRedirect("/tranning-login?error=true&message=" + encodedMessage);
                })
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
                getRedirectStrategy().sendRedirect(request, response, "/tranning-login?error=true&message=" + encodedMessage);
            }
        };
    }
}