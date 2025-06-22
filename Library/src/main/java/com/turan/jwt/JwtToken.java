package com.turan.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtToken {


     private static final String  SECRET_KEY = " YnUgY29rIGd1Y2x1IGJpciBzaWZyZWRpciBtZW5pbSBhZGltIHR1cmFuZGlyCg==";


     public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*3))
                 .signWith(getKey(), SignatureAlgorithm.HS256).compact();
     }


      public <A> A exportToken (String token, Function<Claims,A> claimsAFunction){
              Claims claims =  Jwts.parserBuilder()
                       .setSigningKey(getKey())
                       .build().parseClaimsJws(token).getBody();

              return claimsAFunction.apply(claims);
      }


       public String getUsernameByToken(String token){
               return exportToken(token,Claims::getSubject);
       }

       public boolean isTokenValid(String token){
           Date expireDate =exportToken(token,Claims::getExpiration);
             return new Date().before(expireDate);
       }


   private Key getKey(){
           return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
   }

}
