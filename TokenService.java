package com.project.back_end.services;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service public class TokenService {
 private final String secret;
 private final long expiration;
 public TokenService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){this.secret=secret;this.expiration=expiration;}
 private SecretKey getSigningKey(){return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String generateToken(String email){return issue(email,"PATIENT");}
 public String issue(String email,String role){return Jwts.builder().subject(email).claim("role",role).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).signWith(getSigningKey()).compact();}
 public String email(String token){return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();}
 public String role(String token){return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().get("role",String.class);}
}
