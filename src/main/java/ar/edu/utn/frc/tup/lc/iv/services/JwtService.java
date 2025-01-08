package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {

    String generateToken(UserEntity userEntity);
    String generateRefreshToken(UserEntity userEntity);

    String extractUserName(String refreshToken);
    boolean isTokenValid(String refreshToken ,UserEntity userEntity);

}
