package br.com.gamescan.appuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/index/user{id}/{encodedPassword}")
    public String showUserHomePage(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppUser appUser = appUserService.get(id);
            model.addAttribute("userId", id);
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("userName", appUser.getFirstName());
            return "user/index";
        } catch (AppUserNotFoundException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/user/register")
    public String showNewRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        model.addAttribute("pageTitle", "Criar conta");
        return "/forms/user_register";
    }

    @PostMapping("/user/save")
    public String saveUser(AppUser appUser) {
        appUserService.save(appUser);
        return "redirect:/index/user" + appUser.getId() + "/" + appUser.getPasswordUrl();
    }

    @GetMapping("/admin{adminId}/edit/user{id}/{encodedPassword}")
    public String showEditForm(@PathVariable(name = "adminId") Integer adminId, @PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppUser appUser = appUserService.get(id);
            model.addAttribute("user", appUser);
            model.addAttribute("pageTitle", "Editar usuário (ID: " + id + ")");
            return "/forms/user_register";
        } catch (AppUserNotFoundException e) {
            return "redirect:/users";
        }
    }

    @GetMapping("/admin{adminId}/delete/user{id}/{encodedPassword}")
    public String deleteUser(@PathVariable(name = "adminId") Integer adminId, @PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword) {
        try {
            appUserService.delete(id);
        } catch (AppUserNotFoundException e) {
            System.out.println("Erro");
        }
        return "redirect:/dashboard/admin" + adminId + "/" + encodedPassword + "/list/users";
    }
}