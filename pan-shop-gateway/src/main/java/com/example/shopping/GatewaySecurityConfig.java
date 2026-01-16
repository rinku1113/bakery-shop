package com.example.shopping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Cloud Gateway のセキュリティ設定
 * JWT トークンの検証を行う
 */
@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

    @Value("${app.jwt.issuer-url:https://localhost:8443}")
    private String issuerUrl;

    /**
     * Gateway 用の SecurityFilterChain
     * JWT トークンを検証してリクエストを許可
     */
    @Bean
    public SecurityFilterChain gatewaySecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/gateway/health", "/api/gateway/validate").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder())
                )
            );

        return http.build();
    }

    /**
     * JWT デコーダー
     * Authorization Server の JWK Set エンドポイントから公開鍵を取得
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        String jwksUri = issuerUrl + "/oauth2/jwks";
        return NimbusJwtDecoder.withJwkSetUri(jwksUri).build();
    }
}
