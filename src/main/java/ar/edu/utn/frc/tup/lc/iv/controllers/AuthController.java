package ar.edu.utn.frc.tup.lc.iv.controllers;


import ar.edu.utn.frc.tup.lc.iv.dtos.common.LoginUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.RegisterUserDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.TokenResponseDto;
import ar.edu.utn.frc.tup.lc.iv.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Creates a new user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TokenResponseDto> register(@RequestBody @Valid RegisterUserDto request) {
        final TokenResponseDto token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates a user and returns a JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginUserDto request) {
        final TokenResponseDto token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh token",
            description = "Generates a new access token using a refresh token. Add 'Bearer ' prefix before the token.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Format: Bearer + space + token (Example: Bearer eyJhbG...)",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string")
                    )
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Missing or malformed Authorization header"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TokenResponseDto> refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION)
            @Parameter(hidden = true) final String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Authorization header must start with 'Bearer '");
        }

        TokenResponseDto response = authService.refreshToken(authHeader);
        return ResponseEntity.ok(response);
    }
}
