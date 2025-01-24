package hua.project.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public abstract class ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Retrieve the HTTP status code
        int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == 404) {
            return "error/404"; // Render 404.html
//        } else if (statusCode == 500) {
//            return "error/500"; // Render 500.html
        }

        model.addAttribute("status", statusCode);
        return "error/error"; // Render generic error.html
    }

//    // Optionally override getErrorPath() (deprecated in recent Spring Boot versions)
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }


}

