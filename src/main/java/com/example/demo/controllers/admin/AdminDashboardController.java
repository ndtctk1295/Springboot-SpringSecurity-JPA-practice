package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminDashboardController {
    @GetMapping("/admin/dashboard")
    public String dashboard(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
        return "dashboard";
    }
}
