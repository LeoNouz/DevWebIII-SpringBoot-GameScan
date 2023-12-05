package br.com.gamescan.listas;

import br.com.gamescan.games.GameRepository;
import br.com.gamescan.listas.ZeradosRepository;
import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.appuser.AppUserRepository;
import br.com.gamescan.games.Game;
import br.com.gamescan.listas.Zerados;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ZeradosController {

    private final ZeradosRepository zeradosRepository;
    private final AppUserRepository appUserRepository;
    private final GameRepository gameRepository;

    @Transactional
    @PostMapping("/zerados/save")
    public ResponseEntity<String> adicionarJogo(@RequestParam Long id_jogo, @RequestParam Integer id_usuario, @RequestParam String nome, @RequestParam Date data) {
        try {
            Optional<AppUser> appUserOptional = appUserRepository.findById(id_usuario);
            if (appUserOptional.isPresent()) {
                AppUser appUser = appUserOptional.get();
                Game game;
                Optional<Game> gameOptional = gameRepository.findById(id_jogo);
                if(gameOptional.isPresent()) {
                    game = gameOptional.get();
                } else {
                    game = new Game(nome, data);
                    game.setId(id_jogo);
                    gameRepository.save(game);
                }

                Zerados novoJogo = new Zerados(game, appUser);
                zeradosRepository.save(novoJogo);
            }
            return ResponseEntity.ok("Jogo adicionado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar o jogo à lista.");
        }
    }

    @GetMapping("/zerados/adicionar")
    public String adicionarJogoSecurity() {
        return null;
    }

    @Transactional
    @DeleteMapping("/zerados/delete")
    public ResponseEntity<String> removerJogo(@RequestParam Long id_jogo, @RequestParam Integer id_usuario) {
        try {
            Optional<AppUser> appUserOptional = appUserRepository.findById(id_usuario);
            if (appUserOptional.isPresent()) {
                AppUser appUser = appUserOptional.get();
                Optional<Game> gameOptional = gameRepository.findById(id_jogo);

                if(gameOptional.isPresent()) {
                    Game game = gameOptional.get();
                    Zerados zerados = zeradosRepository.findByJogoAndAppUser(game.getId(), appUser.getId());

                    if (zerados != null) {
                        zeradosRepository.delete(zerados);
                        return ResponseEntity.ok("Jogo removido com sucesso.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado na lista.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado.");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover o jogo à lista.");
        }
    }

    @GetMapping("games/user{id}/{encodedPassword}/list/zerados")
    public String listarJogosJogados(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppUser appUser = appUserRepository.getById(id);

            List<Zerados> meusJogos = zeradosRepository.findByUserId(id);
            List<Long> idsJogos = new ArrayList<>();

            for (Zerados jogo : meusJogos) {
                idsJogos.add(jogo.getJogo().getId());
            }

            model.addAttribute("idsJogos", idsJogos);
            model.addAttribute("userId", id);
            model.addAttribute("userName", appUser.getFirstName());
            model.addAttribute("encodedPassword", encodedPassword);
            return "/user/lista_zerados";
        } catch (Exception e) {
            return "redirect:/error-page";
        }
    }

    @GetMapping("/games/list/zerados")
    public String listarJogosZeradosSecurity() {
        return null;
    }
}