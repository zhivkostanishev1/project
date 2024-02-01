package com.example.fetcher.core.util;

import com.example.fetcher.core.data.User;
import com.example.fetcher.core.exception.AuthenticationTokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .subject("authentication")
                .issuedAt(now)
                .claim("userName", user.getUserName())
                .claim("password", user.getPassword())
                .expiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public User getUserFromToken(String token) {
        if (isTokenExpired(token)) {
            throw new RuntimeException("Token expired, please generate new one");
        }
        Claims allClaims = getAllClaimsFromToken(token);

        return User.builder()
                .userName(getUserNameFromToken(allClaims))
                .password(getPasswordFromToken(allClaims))
                .build();
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private String getUserNameFromToken(Claims claims) {
        return claims.get("userName").toString();
    }

    private String getPasswordFromToken(Claims claims) {
        return claims.get("password").toString();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).build().parseSignedClaims(token).getBody();
        } catch(ExpiredJwtException e) {
            throw new AuthenticationTokenExpiredException("Token expired, please generate new one");
        }
    }
}
