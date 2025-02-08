package jnu.edu.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // 从配置文件中读取密钥
    private String secret;

    @Value("${jwt.expiration}") // Token 有效期（单位：毫秒）
    private long expiration;

    // 生成 Token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    // 解析 Token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token, String username) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject().equals(username) && !isTokenExpired(claims);
    }

    // 检查 Token 是否过期
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}