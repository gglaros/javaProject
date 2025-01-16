package hua.project.Controllers;

import hua.project.Entities.User;
import hua.project.Repository.RoleRepository;
import hua.project.Service.UserService;
import hua.project.Service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    private RoleRepository roleRepository;
    private ValidationService validationService;

    public UserController(UserService userService, RoleRepository roleRepository, ValidationService validationService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.validationService = validationService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
      //  model.addAttribute("roles", roleRepository.findAll());
        List<String> roleNames = Arrays.asList("ROLE_OWNER", "ROLE_TENANT");
        model.addAttribute("roles", roleRepository.findAllByNameIn(roleNames));
        return "auth/register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result ,@RequestParam("roleId") int roleId, Model model){
        try {userService.saveUser(user, roleId); } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "auth/register";
        }
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