package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Entities.Tenant;
import hua.project.Entities.User;
import hua.project.Repository.RoleRepository;
import hua.project.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private OwnerService ownerService;
    private RoleRepository roleRepository;
    private ValidationService validationService;
    private TenantService tenantService;
    private PropertyService propertyService;
    private OwnerApplicationService ownerApplicationService;
    private TenantApplicationService tenantApplicationService;

    public UserController(UserService userService, RoleRepository roleRepository, ValidationService validationService, OwnerService ownerService, TenantService tenantService, PropertyService propertyService, OwnerApplicationService ownerApplicationService, TenantApplicationService tenantApplicationService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.validationService = validationService;
        this.ownerService = ownerService;
        this.tenantService = tenantService;
        this.propertyService = propertyService;
        this.ownerApplicationService = ownerApplicationService;
        this.tenantApplicationService = tenantApplicationService;
    }
    @Operation(
            summary = "User Registration",
            description = "Displays the registration form for creating a new user account.",
            tags = {"User Management"}
    )
    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
      //  model.addAttribute("roles", roleRepository.findAll());
        List<String> roleNames = Arrays.asList("ROLE_OWNER", "ROLE_TENANT");
        model.addAttribute("roles", roleRepository.findAllByNameIn(roleNames));
        return "auth/register";
    }

    @Operation(
            summary = "Save User Registration",
            description = "Processes the form data and saves the user registration details.",
            tags = {"User Management"}
    )
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result ,@RequestParam("roleId") int roleId, Model model){
        try {userService.saveUser(user, roleId); } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "auth/register";
        }
        return "index";
    }

    @Operation(
            summary = "View all users",
            description = "Displays a list of all users in the system. Accessible by admins only.",
            tags = {"User Management"}
    )
    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }


    @GetMapping("/user/delete/{userId}")
    public String deleteOwnerTenantAndUserByUserId(@PathVariable @ModelAttribute int userId, Model model) {

        try{
            User user = userService.getUserById(userId);
            if (user == null) {
                System.out.println("User not found with id: " + userId);
                return "index"; }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try{
            Owner owner = ownerService.getOwnerByUserId(userId);
            if (owner != null) {
                ownerApplicationService.deleteAllApplicationsByOwnerId(owner.getId());
                propertyService.deleteAllPropertiesByOwnerId(owner.getId());
                ownerService.deleteOwnerById(owner.getId());
                System.out.println("Owner deleted with id: " + owner.getId());
            }
        }catch (IllegalArgumentException ex) {
            System.out.println("Owner not found with id: " + userId);
        }

        try {
            Tenant tenant = tenantService.getTenantByUserId(userId);
             if (tenant != null) {
                int tenantId = tenant.getId();
                tenantApplicationService.deleteAllApplicationsByTenantId(tenantId);
                tenantService.deleteTenantById(tenantId);
                System.out.println("tenant deleted with id: " + tenant.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Owner owner = ownerService.getOwnerByUserId(userId);
            Tenant tenant = tenantService.getTenantByUserId(userId);
            if (owner == null || tenant == null) {
                System.out.println("Owner not found with id: " + userId);
                userService.deleteUserById(userId);
                System.out.println("User deleted with id: " + userId);
            }
        }catch (IllegalArgumentException ex) {
            System.out.println("Owner not found with id: " + userId);
        }

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

}