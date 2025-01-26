package hua.project.Controllers;


import hua.project.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {
    private UserService userService;

    @Operation(
            summary = "Home Page",
            description = "Displays the home page of the application. This is the main landing page for users.",
            tags = {"Home"}
    )
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "index";
        //home
    }



}