package com.ex01.basic.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey")
    private String secretKey;
    private final long expirationMs = 5000 * 60;
    public String generateToken(String username, String role){
        System.out.println("jwt secretKey : " + secretKey);
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);
        return Jwts.builder()
                //.setSubject(username)
                //.claim("username", username)
                //.claim("name","추가 이름")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    //JWT 토큰 검증
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey) //parser(해석)하는 객체 생성 및 서명 검증 키 설정
                    .parseClaimsJws(token); // 실제 검증 실행
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //검증 + 내용 꺼내기
    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();//getBody: payload 꺼내기
    }//토큰에서 username 값을 꺼내기
    public String getUsernameFormToken(String token){
        Claims claims = getClaims(token); //Claims 꺼내기
        System.out.println("username : "+claims.get("username",String.class));
        System.out.println("name : "+claims.get("name",String.class));
        return claims.get("username", String.class);
    }

}
