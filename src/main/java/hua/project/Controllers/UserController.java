//package hua.project.Controllers;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//private  UserService userService;
//
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    public String addUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//         return "userForm";
//    }
//
//    @PostMapping("/new")
//    public void saveStudent(@RequestBody User user) {
//        userService.save(user);
//
//    }
//
//
//    @GetMapping("/show")
//    public List<User> show(){
//        return userService.getAllUsers();
//    }
//
//
//    @DeleteMapping(path = "/delete/{userId}")
//    public void deleteUser(@PathVariable("userId") Integer userId) {
//        userService.deleteById(userId);
//    }
//
//
//
//}
