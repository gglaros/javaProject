package hua.project.Service;

import hua.project.Entities.Role;
import hua.project.Entities.Tenant;
import hua.project.Entities.User;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private TenantRepository tenantRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder,TenantRepository tenantRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tenantRepository = tenantRepository;
    }


    @Transactional
    public Integer saveUser(User user) {
        System.out.println("i am in");
        String passwd= user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName("ROLE_TENANT")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();

        Tenant tenant = new Tenant();
        tenant.setFirstName(user.getUsername());
        tenant.setLastName(user.getUsername());
        tenant.setEmail(user.getEmail());
        tenant.setPhone("63484548");
        tenantRepository.save(tenant);

        roles.add(role);
        user.setRoles(roles);

        user = userRepository.save(user);

        return user.getId();
    }





    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);

        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with email: " +username +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.toString()))
                            .collect(Collectors.toSet())
            );
        }
    }
}
