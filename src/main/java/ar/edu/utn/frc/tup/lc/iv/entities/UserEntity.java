package ar.edu.utn.frc.tup.lc.iv.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * La clase {@code UserEntity} representa un usuario.
 * Referencia a la tabla llamada "users".
 */
@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity  {
    /**
     * Identificador único de un usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre real del usuario.
     */
    @Column
    private String name;

    /**
     * Contraseña del usuario utilizada en login.
     */
    @Column
    private String password;

    /**
     * Email del usuario utilizada en login.
     */
    @Column(unique = true)
    private String email;

    /**
     * Listado de tokens referidos al usuario.
     */
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<TokenEntity> tokens;

}
