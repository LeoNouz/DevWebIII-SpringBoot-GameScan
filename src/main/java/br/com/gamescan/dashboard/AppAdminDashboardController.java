package br.com.gamescan.dashboard;

import br.com.gamescan.appadmin.AppAdmin;
import br.com.gamescan.appadmin.AppAdminService;
import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.appadmin.AppAdminNotFoundException;
import br.com.gamescan.games.Game;
import br.com.gamescan.games.GameRepository;
import br.com.gamescan.listas.*;
import br.com.gamescan.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class AppAdminDashboardController {

    private final AppAdminService appAdminService;
    private final AppUserService appUserService;
    private final GameRepository gameRepository;
    private final JogandoRepository jogandoRepository;
    private final ParaJogarRepository paraJogarRepository;
    private final ZeradosRepository zeradosRepository;

    @GetMapping("/dashboard/admin{id}/{encodedPassword}")
    public String showAdminDashboard(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "/admin/dashboard";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/index/admin" + id + "/" + encodedPassword;
        }
    }

    @GetMapping("/dashboard/admin{id}/{encodedPassword}/list/admins")
    public String showAdminList(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        List<AppAdmin> listAppAdmins = appAdminService.listAll();
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("listAdmins", listAppAdmins);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "admin/admins";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/template_dashboard/admin/" + id;
        }
    }

    @GetMapping("/dashboard/admin{id}/{encodedPassword}/list/users")
    public String showUserList(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        List<AppUser> listAppUsers = appUserService.listAll();
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            Integer adminId = appAdmin.getId();
            String adminName = appAdmin.getFirstName();
            model.addAttribute("listUsers", listAppUsers);
            model.addAttribute("adminId", adminId);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", adminName);
            return "admin/users";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/template_dashboard/admin/" + id;
        }
    }

    @GetMapping("/dashboard/admin{id}/{encodedPassword}/list/jogos")
    public String showGamesList(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        List<Game> listGames = gameRepository.findAll();

        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("listGames", listGames);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "admin/jogos";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/template_dashboard/admin/" + id;
        }
    }

    @GetMapping("/dashboard/admin{id}/{encodedPassword}/list/jogando")
    public String showJogandoList(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        List<Jogando> listJogando = jogandoRepository.findAll();

        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("listJogando", listJogando);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "admin/jogando";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/template_dashboard/admin/" + id;
        }
    }

    @GetMapping("/dashboard/admin{id}/{encodedPassword}/list/parajogar")
    public String showParaJogarList(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        List<ParaJogar> listParaJogar = paraJogarRepository.findAll();

        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("listParaJogar", listParaJogar);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "admin/parajogar";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/template_dashboard/admin/" + id;
        }
    }

    @GetMapping("/dashboard/admin{id}/{encodedPassword}/list/zerados")
    public String showZeradosList(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        List<Zerados> listZerados = zeradosRepository.findAll();

        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("listZerados", listZerados);
            model.addAttribute("adminId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "admin/zerados";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/template_dashboard/admin/" + id;
        }
    }
}