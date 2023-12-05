package br.com.gamescan.listas;

import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.games.Game;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Jogando {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_jogo")
    private Game jogo;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    public Jogando(Game jogo, AppUser appUser) {
        this.jogo = jogo;
        this.appUser = appUser;
    }
}