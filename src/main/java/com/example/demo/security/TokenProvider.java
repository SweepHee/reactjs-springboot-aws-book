package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    private static final String SECRET_KEY = "NMA8JPctFuna59f5";

    public String create(UserEntity userEntity) {

        Date expiryDate = Date.from (Instant.now()
                .plus(1, ChronoUnit.DAYS));

        /*
        * // header
        * {
        *   "alg": "HS512"
        * },
        * { // payload
        *   "sub": "40288093784915d2017894342340001",
        *   "iss": "demo app",
        *   "iat": 1595733657,
        *   "exp": 1596597657,
        * }
        * // SECRET_KEY를 이용해 서명한 부분
        * sjfksflsldfjsfjslfjsfjsfksdfskfjsfsfsfsfsdfjsffsf ...(예시임)
        * */

        // JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // header에 들어갈 내용 및 서명을 위한 SECRET_KEY
                // payload에 들어갈 내용
                .setSubject(userEntity.getId()) // sub
                .setIssuer("demo app") // iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
