package top.cadros.onlinevotingsystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import top.cadros.onlinevotingsystem.object.User;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>JWT服务类，用于生成和解析JWT</p>
 * @author 高洪森
 */
@Component
public class JwtService {
    private static String secret;

    @Autowired
    private void initialize(){
        SecureRandom secureRandom = new SecureRandom();
        int keyLength = 256;
        byte[] key = new byte[keyLength / 8];
        secureRandom.nextBytes(key);
        JwtService.secret= Base64.getEncoder().encodeToString(key);
    }

    public static String generateToken(User user) throws Exception {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claim("account", user.getAccount())
                .claim("password", user.getPassword())
                .subject(user.getAccount())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .signWith(key)
                .compact();
    }

    public static Claims getClaimsFromToken(String token) throws Exception {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        JwtParser jwtParser=Jwts.parser()
                .verifyWith(key)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        return jws.getPayload();
    }
}
