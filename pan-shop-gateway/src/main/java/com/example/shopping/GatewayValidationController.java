package com.example.shopping;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gateway 側の JWT 検証コントローラー
 * /api/gateway/validate エンドポイントを提供
 */
@RestController
@RequestMapping("/api/gateway")
public class GatewayValidationController {

    @Value("${app.jwt.issuer-url:https://localhost:8443}")
    private String issuerUrl;

    /**
     * JWT トークンを検証するエンドポイント
     * Authorization ヘッダーから JWT を取得し、検証結果を返す
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateJwt(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "valid", false,
                    "error", "Missing or invalid Authorization header"
                ));
        }

        String token = authHeader.substring(7);

        try {
            JwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(issuerUrl + "/oauth2/jwks").build();
            Jwt jwt = decoder.decode(token);

            java.time.Instant expiresAt = jwt.getExpiresAt();
            return ResponseEntity.ok(Map.of(
                "valid", true,
                "subject", jwt.getSubject() != null ? jwt.getSubject() : "",
                "issuer", jwt.getIssuer() != null ? jwt.getIssuer().toString() : "",
                "expiresAt", expiresAt != null ? expiresAt.toString() : "",
                "authorities", jwt.getClaimAsStringList("authorities") != null 
                    ? jwt.getClaimAsStringList("authorities") 
                    : java.util.Collections.emptyList()
            ));

        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "valid", false,
                    "error", "Invalid JWT token: " + e.getMessage()
                ));
        }
    }

    /**
     * JWT 検証のヘルスチェック
     */
    @org.springframework.web.bind.annotation.GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok", "service", "gateway-jwt-validation"));
    }
}
