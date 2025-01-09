package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Long> {
    @Query(value = """
    select t from TokenEntity t inner join UserEntity u
    on t.user.id = u.id
    where u.id = :userId and (t.expired = false or t.revoked = false)
    """)
    List<TokenEntity> findAllValidTokenByUser(Long userId);

    TokenEntity findByToken(String token);

}
