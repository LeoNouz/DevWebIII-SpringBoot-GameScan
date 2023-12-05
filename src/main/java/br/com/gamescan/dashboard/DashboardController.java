package br.com.gamescan.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "/admin/dashboard";
    }

    @GetMapping("/control-panel/{password}")
    public String showControlPanel(@PathVariable(name = "password") String password) {

        if ("0987l".equals(password)) {
            return "/admin/control_panel";
        } else {
            return "redirect:/senha-incorreta";
        }
    }
}