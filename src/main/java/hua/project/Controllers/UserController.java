package hua.project.Controllers;

import hua.project.Entities.User;
import hua.project.Repository.RoleRepository;
import hua.project.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    private RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result ,@RequestParam("roleId") int roleId, Model model){
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "The username is already taken. Please choose another one.");
            model.addAttribute("roles", roleRepository.findAll());
            return "auth/register";
        }
        userService.saveUser(user,roleId);

        return "index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

}
