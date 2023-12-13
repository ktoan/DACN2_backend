package ecommerce.project.backend.config;

import ecommerce.project.backend.config.jwt.JwtAuthenticationEntryPoint;
import ecommerce.project.backend.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String[] PUBLIC_POST_ALLOWED_ROUTES = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/confirm-registration",
            "/api/v1/auth/send-confirm-code",
    };

    private final String[] PUBLIC_GET_ALLOWED_ROUTES = {
            "/test-connection",
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/api/v1/users/*",
            "/api/v1/users/**",
            "/api/v1/posts/*",
            "/api/v1/posts/**",
            "/api/v1/categories/*",
            "/api/v1/categories/**",
            "/api/v1/products/*",
            "/api/v1/products/**",
    };

    private final String[] ADMIN_GET_ALLOW_ROUTES = {
            "/api/v1/users",
    };

    private final String[] ADMIN_POST_ALLOW_ROUTES = {
            "/api/v1/posts/*",
            "/api/v1/posts/**",
            "/api/v1/categories/*",
            "/api/v1/categories/**",
            "/api/v1/products/*",
            "/api/v1/products/**",
    };

    private final String[] ADMIN_PUT_ALLOW_ROUTES = {
            "/api/v1/categories/*",
            "/api/v1/categories/**",
            "/api/v1/products/*",
            "/api/v1/products/**",
            "/api/v1/posts/*",
            "/api/v1/posts/**",
    };

    private final String[] ADMIN_DELETE_ALLOW_ROUTES = {
            "/api/v1/categories/*",
            "/api/v1/categories/**",
            "/api/v1/products/*",
            "/api/v1/products/**",
            "/api/v1/posts/*",
            "/api/v1/posts/**",
            "/api/v1/users/*",
            "/api/v1/users/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, ADMIN_GET_ALLOW_ROUTES)
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, ADMIN_POST_ALLOW_ROUTES)
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, ADMIN_PUT_ALLOW_ROUTES)
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, PUBLIC_POST_ALLOWED_ROUTES)
                .permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_GET_ALLOWED_ROUTES)
                .permitAll()
                .antMatchers(HttpMethod.DELETE, ADMIN_DELETE_ALLOW_ROUTES)
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
