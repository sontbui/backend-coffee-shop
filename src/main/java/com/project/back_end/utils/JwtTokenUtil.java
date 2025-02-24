package com.project.back_end.utils;

import com.project.back_end.models.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class JwtTokenUtil {

    private String SECRET;
    private  SecretKey key;
    
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.SECRET = secret;
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    @Value("${jwt.expiration}")
    private Long expiration;

   

    public String generateToken(UserAccount userAccount) {
        return Jwts.builder()
                .setSubject(userAccount.getEmail())
                .claim("role", userAccount.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean validateToken(String token, UserAccount userAccount) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userAccount.getEmail()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}