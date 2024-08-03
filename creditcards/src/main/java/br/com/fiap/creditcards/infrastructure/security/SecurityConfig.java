package br.com.fiap.creditcards.infrastructure.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

import br.com.fiap.creditcards.infrastructure.exception.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private static final String URL_CREDITCARDS = "/api/cartao";
  public static final String V3_API_DOCS = "/v3/api-docs/**";
  public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
  public static final String SWAGGER_UI = "/swagger-ui/**";
  private final SecurityFilter securityFilter;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  public SecurityConfig(SecurityFilter securityFilter,
      CustomAccessDeniedHandler customAccessDeniedHandler) {
    this.securityFilter = securityFilter;
    this.customAccessDeniedHandler = customAccessDeniedHandler;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(req -> {
          req.requestMatchers(POST, URL_CREDITCARDS).authenticated();
          req.requestMatchers(GET, URL_CREDITCARDS + "/**").authenticated();
          req.requestMatchers(PATCH, URL_CREDITCARDS + "/**").authenticated();
          req.requestMatchers(V3_API_DOCS, SWAGGER_UI_HTML, SWAGGER_UI).permitAll();
          req.anyRequest().denyAll();
        })
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(
                customAccessDeniedHandler).authenticationEntryPoint(new HttpStatusEntryPoint(
                HttpStatus.UNAUTHORIZED)))
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
