package com.skachko.shop.auth.service.utils;

import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.exceptions.UnauthorizedException;
import com.skachko.shop.auth.service.support.utils.IsEmptyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public static final long JWT_TOKEN_VALIDITY = 30 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        try {
            return IsEmptyUtil.isNullOrEmpty(token) || this.isTokenExpired(token);
        } catch (Exception e){
            return true;
        }
    }


    public String generateToken(CustomUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getRoles());
        claims.put("name", user.getName());
        claims.put("version", user.getDtUpdate());
        return doGenerateToken(claims, user.getId().toString());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return "Bearer " + Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
