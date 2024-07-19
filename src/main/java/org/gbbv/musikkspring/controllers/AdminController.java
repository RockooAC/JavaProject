package org.gbbv.musikkspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/adminActions")
    public String showAdminActions() {
        return "adminActions";
    }
}