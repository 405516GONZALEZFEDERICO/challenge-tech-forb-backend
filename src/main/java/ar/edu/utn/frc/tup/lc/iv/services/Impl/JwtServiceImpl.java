package ar.edu.utn.frc.tup.lc.iv.services.Impl;

import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import ar.edu.utn.frc.tup.lc.iv.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshExpiration;
    @Override
    public String generateToken(UserEntity userEntity) {
        return  buildToken(userEntity,jwtExpiration);
    }
    @Override
    public String generateRefreshToken(UserEntity userEntity) {
        return  buildToken(userEntity,refreshExpiration);
    }

    @Override
    public String extractUserName(String refreshToken) {
        final Claims jwtToken=Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        return jwtToken.getSubject();
    }

    @Override
    public boolean isTokenValid(final String refreshToken, UserEntity userEntity) {
        final String username=extractUserName(refreshToken);
        return (username.equals(userEntity.getEmail())) && !isTokenExpired(refreshToken);
    }

    private boolean isTokenExpired(final String refreshToken) {
        return  extractExpiration(refreshToken).before(new Date());
    }

    private Date extractExpiration(final String refreshToken) {
        final Claims jwtToken=Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        return jwtToken.getExpiration();
    }

    private String buildToken(final UserEntity userEntity, final long expiration) {
    return Jwts.builder()
            .id(userEntity.getId().toString())
            .claims(Map.of("name", userEntity.getName()))
            .subject(userEntity.getEmail())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+expiration))
            .signWith(getSignInKey())
            .compact();
    }
    private SecretKey getSignInKey(){
        byte[]keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
