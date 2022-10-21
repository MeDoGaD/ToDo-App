package com.cis.springboot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TokenUtil {
   private final String ClaimsSubject="sub";
    private final String ClaimsCreated="created";

    @Value("${auth.expiration}")
    private Long Token_Validity = 604800L;

    @Value("${auth.secret}")
    private String Token_Secret;

    public String generateToken(UserDetails userDetails){
        //clains >> expration >> sing >>compact
        Map<String , Object> claims=new HashMap<>();
        claims.put(ClaimsSubject,userDetails.getUsername());
        claims.put(ClaimsCreated,new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,Token_Secret)
                .compact();
    }

    private Date generateExpirationDate() {
     return new Date(System.currentTimeMillis()+Token_Validity*1000);
    }

    public String getUsernameFromToken(String token)
    {
        try{
            Claims claims=getClaims(token);
         return claims.getSubject();
        }
        catch (Exception ex){
          return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
     Date expiration = getClaims(token).getExpiration();
     return expiration.before(new Date());
    }
    private Claims getClaims(String token)
    {
        Claims claims;
        try {
             claims= Jwts.parser().setSigningKey(Token_Secret).parseClaimsJws(token).getBody();
        }catch (Exception ex){
            claims=null;
        }
        return claims;
    }
}
