package hua.project.Controllers;
import hua.project.Entities.Role;
import hua.project.Entities.User;
import hua.project.Repository.RoleRepository;
import hua.project.Repository.UserRepository;
import hua.project.Service.ValidationService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.HashSet;
import java.util.Set;

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
                System.out.println("mpikaa!!");
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

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

}
