package br.com.gamescan.dashboard;

import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.appuser.AppUserService;
import br.com.gamescan.appuser.AppUserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppUserDashboardController {

    @Autowired private AppUserService appUserService;

    @GetMapping("/dashboard/user{id}/{encodedPassword}")
    public String showUserDashboard(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppUser appUser = appUserService.get(id);
            model.addAttribute("userId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("userName", appUser.getFirstName());
            return "/user/dashboard";
        } catch (AppUserNotFoundException e) {
            return "redirect:/index/user" + id + "/" + encodedPassword;
        }
    }
}