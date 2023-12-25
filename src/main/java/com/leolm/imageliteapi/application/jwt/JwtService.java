package com.leolm.imageliteapi.application.jwt;


import com.leolm.imageliteapi.domain.AcessToken;
import com.leolm.imageliteapi.domain.entity.User;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private SecretKeyGenerator keyGenerator;

    public AcessToken generateToken(User user){
        SecretKey key = keyGenerator.getKey();

        Jwts.builder()
                .signWith(key)
                .subject(user.getEmail())
                .expiration(generateTokenExpiration())
                .claims(generateTokenClaims(user))
                .compact();
        return  new AcessToken("");
    }

    private Date generateTokenExpiration(){
        int MINUTES_TO_EXPIRE = 60;
        return Date.from(LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE).atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    private Map<String, Object> generateTokenClaims(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name",user.getName());

        return claims;
    }
}
