package com.example.demo.security;

import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final UserServiceImp userServiceImp;
    @Value("${spring.app.JwtSecret}")
    private String secret ;
    @Value("${spring.app.JwtExpirationMs}")
    private String JwtExpirationMs;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public AppUser getUserfromRequest(HttpServletRequest request) {
        String jwt =getJwFromHeader(request);
        String username=extractUsername(jwt);
        AppUser user=userServiceImp.findUserByUserName(username);
        return user;

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String authToken)
    {
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            if (isTokenExpired(authToken))
            {
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +  1000 * 60 * 60 * 24 * 7 ))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    private Key key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

        public String getJwFromHeader(HttpServletRequest httpRequest)
    {
        String bearerToken=httpRequest.getHeader("Authorization");
        if (bearerToken!=null&&bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }

}
//
//import static org.yaml.snakeyaml.tokens.Token.ID.Key;
//@Component
//
//public class JwtUtils {
//
//    @Value("${spring.app.JwtSecret}")
//    private String jwtSecret;
//    @Value("${spring.app.JwtExpirationMs}")
//    private int JwtExpirationMs;
//
//    public String getJwFromHeader(HttpServletRequest httpRequest)
//    {
//        String bearerToken=httpRequest.getHeader("Authentication");
//        if (bearerToken!=null&&bearerToken.startsWith("Bearer "))
//        {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//
//    public String generatJwtTokenFromUserName(UserDetails userDetails)
//    {
//        String userName=userDetails.getUsername();
//        return Jwts.builder()
//                .subject(userName)
//                .issuedAt(new Date())
//                .expiration(new Date((new Date()).getTime()+JwtExpirationMs))
//                .signWith(key())
//                .compact();
//    }
//    public String getUserNameFromToken(String token)
//    {
//        return Jwts.parser()
//                .verifyWith((SecretKey) key() )
//                .build().parseSignedClaims(token)
//                .getPayload().getSubject();
//    }
//    private Key key()
//    {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }
//
//    public boolean validateJwtToken(String authToken)
//    {
//        try{
//            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
//            return true;
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }
//}
