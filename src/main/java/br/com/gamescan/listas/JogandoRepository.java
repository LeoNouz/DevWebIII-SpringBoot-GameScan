package br.com.gamescan.listas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogandoRepository extends JpaRepository<Jogando, Long> {

    @Query("SELECT j FROM Jogando j WHERE j.appUser.id = :userId")
    List<Jogando> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT j FROM Jogando j WHERE j.jogo.id = :jogoId AND j.appUser.id = :userId")
    Jogando findByJogoAndAppUser(@Param("jogoId") Long jogoId, @Param("userId") Integer userid);
}