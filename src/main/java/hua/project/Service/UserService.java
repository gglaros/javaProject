package hua.project.Service;

import hua.project.Entities.Owner;
import hua.project.Entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hua.project.Entities.User;
import hua.project.Repository.OwnerRepository;
import hua.project.Repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import hua.project.Repository.RoleRepository;
import hua.project.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
private OwnerRepository ownerRepository;
    private RoleRepository roleRepository;
    private TenantRepository tenantRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder,TenantRepository tenantRepository,ValidationService validationService,OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tenantRepository = tenantRepository;
        this.ownerRepository = ownerRepository;
        this.validationService = validationService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public void saveUser(User user, int roleId) {
        if (validationService.isUsernameTaken(user.getUsername())) { throw new IllegalArgumentException("Username is already taken"); }

        if (validationService.isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");}

        if (!validationService.isPasswordValid(user.getPassword())) {
            logger.warn("Invalid password: does not meet security requirements.");
            throw new IllegalArgumentException("Password must be at least 5 characters long.");
        }

        if(!validationService.isUserNameValid(user.getUsername())) {
            throw new IllegalArgumentException("Username must me between 3 and 20");
        }

        String passwd= user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        user.setPassword(encodedPassword);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();

        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

    }


    @Transactional
    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);
System.out.println("loaduserbyuusername");
        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with email: " +username +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.toString()))
                            .collect(Collectors.toSet())
            );
        }
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

}
