package org.gbbv.musikkspring.controllers;

import jakarta.validation.Valid;
import org.gbbv.musikkspring.model.Role;
import org.gbbv.musikkspring.model.RoleEnum;
import org.gbbv.musikkspring.model.User;
import org.gbbv.musikkspring.repository.RoleRepository;
import org.gbbv.musikkspring.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.gbbv.musikkspring.dto.UserDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Controller
public class AuthController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AuthController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        // Fetch the ROLE_USER from the database and assign it to the new user
        Role userRole = roleRepository.findByName(RoleEnum.USER.name());
        userDto.setRole(userRole.getName());

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/account")
    public String showAccountDetails(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User userDto = userService.findUserByEmail(email);
        model.addAttribute("user", userDto);
        return "accountDetails";
    }
}