package br.com.gamescan.listas;

import br.com.gamescan.listas.Zerados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZeradosRepository extends JpaRepository<Zerados, Long> {

    @Query("SELECT j FROM Zerados j WHERE j.appUser.id = :userId")
    List<Zerados> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT j FROM Zerados j WHERE j.jogo.id = :jogoId AND j.appUser.id = :userId")
    Zerados findByJogoAndAppUser(@Param("jogoId") Long jogoId, @Param("userId") Integer userid);
}