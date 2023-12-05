package br.com.gamescan.games;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Game {
    @Id
    private Long id;

    private String name;
    private Date date;

    public Game(String name, Date date) {
        this.name = name;
        this.date = date;
    }
}