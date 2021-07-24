package br.com.yuri.todomaneiro.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(final String username) {

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public boolean tokenValido(final String token) {
        var claims = this.getClaims(token);
        if(claims != null) {
            var username = claims.getSubject();
            var expirationDate = claims.getExpiration();
            var now = new Date(System.currentTimeMillis());
            if(username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch(Exception e){
            return null;
        }
    }

    public String getUsername(final String token) {
        var claims = this.getClaims(token);
        if(claims != null) {
            return claims.getSubject();
        }
        return null;
    }

}
