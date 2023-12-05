package br.com.gamescan.games;

import br.com.gamescan.appadmin.AppAdmin;
import br.com.gamescan.appadmin.AppAdminNotFoundException;
import br.com.gamescan.appadmin.AppAdminService;
import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.appuser.AppUserNotFoundException;
import br.com.gamescan.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GameController {

    @Autowired private AppUserService appUserService;
    @Autowired private AppAdminService appAdminService;

    @GetMapping("/games")
    public String showGamesPage() {
        return "/games/games";
    }

    @GetMapping("/games/user{id}/{encodedPassword}")
    public String showUserGamesPage(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppUser appUser = appUserService.get(id);
            model.addAttribute("userId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("userName", appUser.getFirstName());
            return "/user/games";
        } catch (AppUserNotFoundException e) {
            return "redirect:/index/user" + id + "/" + encodedPassword;
        }
    }

    @GetMapping("/games/admin{id}/{encodedPassword}")
    public String showAdminGamesPage(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "/admin/games";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/index/admin" + id + "/" + encodedPassword;
        }
    }

    @GetMapping("/game/detalhes/{id}")
    public String showGamesDetailsPage() {
        return "games/game_details";
    }

    @GetMapping("/games/user{id}/{encodedPassword}/game-detalhes/{gameId}")
    public String showUserGameDetailsPage(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppUser appUser = appUserService.get(id);
            model.addAttribute("userId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("userName", appUser.getFirstName());
            return "/user/game_details";
        } catch (AppUserNotFoundException e) {
            return "redirect:/index/user" + id + "/" + encodedPassword;
        }
    }

    @GetMapping("/games/admin{id}/{encodedPassword}/game-detalhes/{gameId}")
    public String showAdminGameDetailsPage(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "/admin/game_details";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/index/admin" + id + "/" + encodedPassword;
        }
    }
}