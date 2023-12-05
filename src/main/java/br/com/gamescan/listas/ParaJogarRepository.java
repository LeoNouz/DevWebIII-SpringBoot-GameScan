package br.com.gamescan.listas;

import br.com.gamescan.listas.ParaJogar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParaJogarRepository extends JpaRepository<ParaJogar, Long> {

    @Query("SELECT j FROM ParaJogar j WHERE j.appUser.id = :userId")
    List<ParaJogar> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT j FROM ParaJogar j WHERE j.jogo.id = :jogoId AND j.appUser.id = :userId")
    ParaJogar findByJogoAndAppUser(@Param("jogoId") Long jogoId, @Param("userId") Integer userid);
}