package ar.edu.utn.frc.tup.lc.iv.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    /**
     * Nombre real del usuario.
     */
    private String name;

    /**
     * Contraseña del usuario utilizada en login.
     */
    private String password;
    /**
     * Email utilizado en login.
     */
    private String email;


}
