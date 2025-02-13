package hua.project.Service;

import hua.project.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalDateTime;

@Service
public class ValidationService {

    private final UserRepository userRepository;

    public ValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUsernameTaken(String username) {return userRepository.existsByUsername(username);}

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isPasswordValid(String password) {

        return password != null && password.length() >= 5 && password.length() <= 120;
    }

    public boolean isUserNameValid(String username) {

        return username != null && username.length() <= 20 && username.length() >= 3;
    }


}
