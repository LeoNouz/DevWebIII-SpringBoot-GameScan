package br.com.gamescan.appadmin;

import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AppAdminController {
    private final AppAdminService appAdminService;
    private final AppUserService appUserService;

    @GetMapping("/index/admin{id}/{encodedPassword}")
    public String showAdminHomePage(@PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("adminId", appAdmin.getId());
            model.addAttribute("encodedPassword", encodedPassword);
            model.addAttribute("adminName", appAdmin.getFirstName());
            return "admin/index";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/admin/register/{password}")
    public String showNewRegisterForm(@PathVariable(name = "password") String password, Model model) {

        if ("FicouBacana30dwiii".equals(password)) {
            model.addAttribute("admin", new AppAdmin());
            model.addAttribute("pageTitle", "Criar conta");
            return "/forms/admin_register";
        } else {
            return "redirect:/senha-incorreta";
        }
    }

    @GetMapping("/admin/new/{encodedPassword}")
    public String showAddNewAdminRegisterForm(Model model) {
        model.addAttribute("admin", new AppAdmin());
        model.addAttribute("pageTitle", "Cadastrar administrador");
        return "/forms/admin_register";
    }

    @GetMapping("/user/new")
    public String showAddNewUserRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        model.addAttribute("pageTitle", "Cadastrar usuário");
        return "/forms/user_register";
    }

    @PostMapping("/admin/save")
    public String saveAdmin(AppAdmin appAdmin) {
        appAdminService.save(appAdmin);
        return "redirect:/index/admin" + appAdmin.getId() + "/" + appAdmin.getPasswordUrl();
    }

    @GetMapping("/admin{adminId}/edit/admin{id}/{encodedPassword}")
    public String showEditForm(@PathVariable(name = "adminId") Integer adminId, @PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword, Model model) {
        try {
            AppAdmin appAdmin = appAdminService.get(id);
            model.addAttribute("admin", appAdmin);
            model.addAttribute("pageTitle", "Editar administrador (ID: " + id + ")");
            return "forms/admin_register";
        } catch (AppAdminNotFoundException e) {
            return "redirect:/admins";
        }
    }

    @GetMapping("/admin{adminId}/delete/admin{id}/{encodedPassword}")
    public String deleteAdmin(@PathVariable(name = "adminId") Integer adminId, @PathVariable(name = "id") Integer id, @PathVariable(name = "encodedPassword") String encodedPassword) {
        try {
            appAdminService.delete(id);
            appAdminService.get(id);
        } catch (AppAdminNotFoundException e) {
            System.out.println("Erro");
        }
        return "redirect:/dashboard/admin" + adminId + "/" + encodedPassword + "/list/admins";
    }
}