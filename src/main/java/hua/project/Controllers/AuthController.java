package hua.project.Controllers;
import hua.project.Entities.Role;
import hua.project.Entities.User;
import hua.project.Repository.RoleRepository;
import hua.project.Repository.UserRepository;
import hua.project.Service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.HashSet;
import java.util.Set;

@Tag(name = "Authentication", description = "Handles user authentication and role initialization.")
@Controller
public class AuthController {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final UserRepository userRepository;


    public AuthController(RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Initialize Roles and Admin User",
            description = "Sets up default roles (OWNER, TENANT, ADMIN) and creates an admin user if not already present."
    )
    @PostConstruct
    public void setup() {
        try {
            Role roleOwner = new Role("ROLE_OWNER");
            Role roleTenant = new Role("ROLE_TENANT");
            Role roleAdmin = new Role("ROLE_ADMIN");

            roleRepository.updateOrInsert(roleOwner);
            roleRepository.updateOrInsert(roleTenant);
            roleRepository.updateOrInsert(roleAdmin);

            User user = new User();
            user.setUsername("admin2");

            if (!validationService.isUsernameTaken(user.getUsername())) {
                String password = "admin2";
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
                user.setEmail("admin2@hua.com");
                Set<Role> roles = new HashSet<>();
                roles.add(roleAdmin);
                user.setRoles(roles);
                userRepository.save(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Operation(
            summary = "Login Page",
            description = "Displays the login page for users to log into the system."
    )
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

}
