package mind_focus.Mind_Focus.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.secretRefresh}")
    private String secretRefreshKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.expirationRefresh}")
    private long expirationRefresh;

    // Métodos Para o access Token (Chave Comum)
    public String extractUsername(String token) {
        return extractClaim(token, secretKey, Claims::getSubject);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isTokenValid(String token, String email) {
        String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token, secretKey);
    }

    // Métodos Refresh Token
    public String extractUsernameFromRefreshToken(String token) {
        return extractClaim(token, secretRefreshKey, Claims::getSubject);
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationRefresh))
                .signWith(SignatureAlgorithm.HS256, secretRefreshKey)
                .compact();
    }

    public boolean isRefreshTokenValid(String token, String email) {
        String username = extractUsernameFromRefreshToken(token);
        return username.equals(email) && !isTokenExpired(token, secretRefreshKey);
    }

    private boolean isTokenExpired(String token, String key) {
        return extractExpiration(token, key).before(new Date());
    }

    private Date extractExpiration(String token, String key) {
        return extractClaim(token, key, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, String key, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return resolver.apply(claims);
    }
}
