package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Repository.UserRepository;
import hua.project.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Owner", description = "Manage owners and their control panel")
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

    @Operation(
            summary = "Get all owners",
            description = "Fetches a list of all registered owners. Accessible only to admins.",
            tags = {"Owner"}
    )
    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showOwners(Model model) {
        model.addAttribute("owners", ownerService.getAllOwners());
        return "owner/ownersList";
    }

    @Operation(
            summary = "View profile",
            description = "Fetches the owner's profile or displays the registration form if no profile exists.",
            tags = {"Owner"}
    )
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

    @Operation(
            summary = "Register a new owner",
            description = "Saves a new owner profile in the system.",
            tags = {"Owner"}
    )
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

    @Operation(
            summary = "Add a property",
            description = "Displays the form to add a new property for a specific owner.",
            tags = {"Property"}
    )
    @Secured({"ROLE_ADMIN","ROLE_OWNER"})
    @GetMapping("/make/property/{id}")
    public String addProperty(@PathVariable int id, Model model) {
        Property property = new Property();
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("property", property);
        model.addAttribute("owner", owner);
        return "property/propertyForm";
    }

    @Operation(
            summary = "Save a new property",
            description = "Saves a new property under the specified owner's profile.",
            tags = {"Property"}
    )
    @Secured({"ROLE_ADMIN","ROLE_OWNER"})
    @PostMapping("/make/property/{id}")
    public String saveProperty(@PathVariable int id, @ModelAttribute("property") Property property, Model model) {
        Owner owner = ownerService.getOwnerById(id);
        ownerService.savePropertyToOwner(owner,property);
        List<Property> properties =propertyService.getAllPropertiesByOwnerId(id);
        model.addAttribute("properties", properties);
        return "property/propertyList";
    }

    @Operation(
            summary = "View owner applications",
            description = "Displays a list of applications submitted by the logged-in owner.",
            tags = {"Owner Application"}
    )
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
    @Operation(
        summary = "View owner's properties",
        description = "Displays all properties owned by the logged-in owner.",
        tags = {"Property"}
    )
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
    @GetMapping("/show/properties/{ownerId}")
    public String viewOwnerPropertiesForAdmin(@PathVariable int id, Model model) {
        // Fetch the owner by ID
        Owner owner = ownerService.getOwnerById(id);
//        if (owner == null) {
//            throw new RuntimeException("Owner not found with ID: " + id);
//        }

        // Fetch properties for the owner
        List<Property> ownerProperties = propertyService.getAllPropertiesByOwnerId(id);

        // Add data to the model
        model.addAttribute("owner", owner);
        model.addAttribute("ownerProperties", ownerProperties);

        // Return the same template as the user-facing method
        return "owner/ownerProperties";
    }
    @Operation(
            summary = "View rental requests",
            description = "Displays all rental requests for the logged-in owner's properties.",
            tags = {"Rental Request"}
    )
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


}
