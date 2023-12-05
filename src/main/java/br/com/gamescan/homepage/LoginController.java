package br.com.gamescan.homepage;

import br.com.gamescan.appadmin.AppAdmin;
import br.com.gamescan.appadmin.AppAdminNotFoundException;
import br.com.gamescan.appadmin.AppAdminService;
import br.com.gamescan.appuser.AppUser;
import br.com.gamescan.appuser.AppUserNotFoundException;
import br.com.gamescan.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired private AppUserService appUserService;
    @Autowired private AppAdminService appAdminService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "/forms/user_login";
    }

    @PostMapping("/login-post")
    public String loginSucess(Model model, HttpServletRequest request) {
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");

        try {
            UserDetails userDetails = appUserService.loadUserByUsername(email);
            Integer userId = appUserService.getUserIdFromUserDetails(userDetails);

            try {
                AppUser appUser = appUserService.get(userId);
                Boolean checkedPassword = appUserService.checkPassword(password, appUser.getPassword());

                if (userId != null && checkedPassword) {
                    return "redirect:/index/user" + userId + "/" + appUser.getPasswordUrl();
                } else {
                    return "redirect:/login";
                }
            } catch (AppUserNotFoundException e) {
                return "redirect:/userNotFound";
            }

        } catch (UsernameNotFoundException e) {
            UserDetails userDetails = appAdminService.loadUserByUsername(email);
            Integer adminId = appAdminService.getAdminIdFromUserDetails(userDetails);

            try {
                AppAdmin appAdmin = appAdminService.get(adminId);
                Boolean checkedPassword = appAdminService.checkPassword(password, appAdmin.getPassword());

                if (adminId != null && checkedPassword) {
                    return "redirect:/index/admin" + adminId + "/" + appAdmin.getPasswordUrl();
                } else {
                    return "redirect:/login";
                }
            } catch (AppAdminNotFoundException e2) {
                return "redirect:/userNotFound";
            }
        }
    }
}