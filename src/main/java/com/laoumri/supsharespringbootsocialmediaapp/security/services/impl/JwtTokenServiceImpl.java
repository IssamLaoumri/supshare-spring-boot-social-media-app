package com.laoumri.supsharespringbootsocialmediaapp.security.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.security.services.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {
    @Value("${jwt.expirationMs}")
    private Long expirationMs;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateJwtTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationMs))
                .signWith(this.key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenFromUsername(String username, Long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(this.key(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(this.key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public boolean isJwtTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e){
            log.error("Invalid Jwt Signature : {}",e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid Jwt Token : {}",e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Expired Jwt Token : {}",e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Unsupported Jwt Token : {}",e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("Jwt Token is empty : {}",e.getMessage());
        }
        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
