package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Repository.UserRepository;
import hua.project.Service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("owner")
public class OwnerController {

    private final UserService userService;
    private final  OwnerService ownerService;
    private final   PropertyService propertyService;
    private final TenantApplicationService tenantApplicationService;
    private final OwnerApplicationService ownerApplicationService;

    public OwnerController(OwnerService ownerService, OwnerApplicationService ownerApplicationService,PropertyService propertyService,  UserService userService, TenantApplicationService tenantApplicationService) {
        this.ownerService = ownerService;
        this.propertyService = propertyService;
        this.userService = userService;
        this.tenantApplicationService = tenantApplicationService;
        this.ownerApplicationService = ownerApplicationService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showOwners(Model model) {
        model.addAttribute("owners", ownerService.getAllOwners());
        return "owner/ownersList";
    }


    @GetMapping("/profile")
    public String viewProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {throw new RuntimeException("User not found");}
        Owner existOwner = ownerService.findByUser(user);
        if (existOwner == null) {
            Owner owner = new Owner();
            model.addAttribute("owner", owner);
            model.addAttribute("user", user);
            return "owner/Owner";
        }
        model.addAttribute("owner", existOwner);
        return "owner/ownerProfile";
    }

    @PostMapping("/new")
    public String saveOwner(@Valid @ModelAttribute("owner") Owner owner, BindingResult result,@RequestParam("userId") int userId, Model model) {
        User user = userService.getUserById(userId);
        if (result.hasErrors()) {
            model.addAttribute("owner", owner);
            return "owner/Owner";
        }
        if (user == null) {throw new RuntimeException("User not found");}
        ownerService.saveOwner(owner,user,user.getEmail());
        model.addAttribute("owners", ownerService.getAllOwners());
        return "index";
    }


@Secured({"ROLE_ADMIN","ROLE_OWNER"})
@GetMapping("/make/property/{id}")
    public String addProperty(@PathVariable int id, Model model) {
        Property property = new Property();
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("property", property);
        model.addAttribute("owner", owner);
        return "property/propertyForm";
    }

    @Secured({"ROLE_ADMIN","ROLE_OWNER"})
    @PostMapping("/make/property/{id}")
    public String saveProperty(@PathVariable int id, @ModelAttribute("property") Property property, Model model) {
        Owner owner = ownerService.getOwnerById(id);
        ownerService.savePropertyToOwner(owner,property);
        List<Property> properties =propertyService.getAllPropertiesByOwnerId(id);
        model.addAttribute("properties", properties);
        return "property/propertyList";
    }

    @Secured("ROLE_OWNER")
    @GetMapping("/OwnerApplications")
    public String ownerApplications(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {throw new RuntimeException("User not found");}
        Owner existOwner = ownerService.findByUser(user);
        if (existOwner == null) {
            Owner owner = new Owner();
            model.addAttribute("owner", owner);
            model.addAttribute("user", user);
            return "owner/Owner";
        }
        int id = existOwner.getId();
        List<OwnerApplication> ownerApplications = ownerApplicationService.getOwnerApplicationsById(id);
        model.addAttribute("owner", existOwner);
        model.addAttribute("ownerApplications", ownerApplications);
        return "applicationOwner/applications";
    }
//    @GetMapping("/OwnerApplications/{userId}")
//    public String ownerApplications(@PathVariable int userId, Model model) {
//        List<OwnerApplication> ownerApplications = ownerApplicationService.getOwnerApplicationsById(userId);
//        model.addAttribute("ownerApplications", ownerApplications);
//        return "applicationOwner/applications";
//    }

    @GetMapping("/show/properties")
    public String viewProperties(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {throw new RuntimeException("User not found");}
        Owner existOwner = ownerService.findByUser(user);
        if (existOwner == null) {
            Owner owner = new Owner();
            model.addAttribute("owner", owner);
            model.addAttribute("user", user);
            return "owner/Owner";
        }
        int id = existOwner.getId();
        List<Property> ownerProperties =propertyService.getAllPropertiesByOwnerId(id);
        model.addAttribute("owner", existOwner);
        model.addAttribute("ownerProperties", ownerProperties);
        return "owner/ownerProperties";
    }
    @GetMapping("/show/requests")
    public String showRentalRequests(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {throw new RuntimeException("User not found");}
        Owner existOwner = ownerService.findByUser(user);
        if (existOwner == null) {
            Owner owner = new Owner();
            model.addAttribute("owner", owner);
            model.addAttribute("user", user);
            return "owner/Owner";
        }
        int id = existOwner.getId();
        List<TenantApplication> ownerRequests = tenantApplicationService.ApplicationsByOwnerId(id);
        model.addAttribute("owner", existOwner);
        model.addAttribute("ownerRequests", ownerRequests);
        return "owner/ownerRequests";
    }
//@GetMapping("show/requests/{ownerId}")
//    public String showOwnerRequests(Model model,@PathVariable int ownerId) {
//        List<TenantApplication> ownerRequests = tenantApplicationService.ApplicationsByOwnerId(ownerId);
//        System.out.println(ownerRequests);
//        model.addAttribute("ownerRequests", ownerRequests);
//        return "owner/ownerRequests";
//}


}
