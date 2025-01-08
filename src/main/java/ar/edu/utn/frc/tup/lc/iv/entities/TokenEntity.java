package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tokens")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenEntity {
    /**
     * Identificador único de un token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * Token generado.
     */
    @Column
    public String token;
    /**
     * Tipo de Token.
     */
    @Enumerated(EnumType.STRING)
    public TokenType type;

    /**
     * Estado del Token.
     */
    @Column
    public  boolean revoked;
    /**
     * Estado del Token.
     */
    @Column
    public  boolean expired;

    /**
     * Usario referido al Token.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;
}
