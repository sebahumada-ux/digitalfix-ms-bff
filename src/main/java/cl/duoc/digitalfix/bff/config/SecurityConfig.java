package cl.duoc.digitalfix.bff.config;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Errores
                        .requestMatchers("/error").permitAll()

                        // CATÁLOGO
                        // Solo Admin y Supervisor
                        .requestMatchers("/api/bff/catalog/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR")

                        // WORKORDERS - cambiar estado
                        // Solo Admin y Supervisor
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/bff/workorders/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR")

                        // WORKORDERS - crear
                        // Admin, Supervisor y Cliente
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/bff/workorders")
                        .hasAnyRole(
                                "ADMIN",
                                "SUPERVISOR",
                                "CLIENTE")

                        // WORKORDERS - consultar
                        // Admin, Supervisor y Cliente
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bff/workorders/**")
                        .hasAnyRole(
                                "ADMIN",
                                "SUPERVISOR",
                                "CLIENTE")

                        // Cualquier otra ruta requiere JWT válido
                        .anyRequest()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter)
                        )
                );

        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken>
    jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                this::extraerRoles
        );

        return converter;
    }

    private Collection<GrantedAuthority> extraerRoles(Jwt jwt) {

        List<String> roles =
                jwt.getClaimAsStringList("roles");

        if (roles == null) {
            return List.of();
        }

        return roles.stream()
                .map(rol ->
                        (GrantedAuthority)
                                new SimpleGrantedAuthority(
                                        "ROLE_" +
                                                rol.toUpperCase(Locale.ROOT)
                                )
                )
                .toList();
    }
}