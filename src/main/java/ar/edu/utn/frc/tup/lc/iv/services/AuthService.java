package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.LoginUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.RegisterUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.TokenResponseDto;
import ar.edu.utn.frc.tup.lc.iv.entities.UserEntity;

public interface AuthService {
    TokenResponseDto register(RegisterUserDto input);
    TokenResponseDto login(LoginUserDto input);
    TokenResponseDto refreshToken(final String authHeader);

    void saveUserToken(UserEntity user,String jwtToken);
}
