package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import hua.project.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Owner Applications", description = "Manage owner applications and their status")
@Controller
@RequestMapping("/OwnerApplications")
public class OwnerApplicationController {
    private final UserService userService;
    private final OwnerApplicationService ownerApplicationService;
    private final OwnerService ownerService;
    private final PropertyService propertyService;

    public OwnerApplicationController(OwnerApplicationService ownerApplicationService, OwnerService ownerService, PropertyService propertyService,UserService userService) {
        this.ownerApplicationService = ownerApplicationService;
        this.ownerService = ownerService;
        this.propertyService = propertyService;
        this.userService = userService;
    }
    @Operation(summary = "View all owner applications", description = "Accessible only by Admin users, lists all the property applications made by the renters to the admin")
    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showApplications(Model model) {
        model.addAttribute("ownerApplications",ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }
    @Operation(summary = "Show application form for an owner", description = "Displays the properties the owner hasn’t submitted an application request for, to be approved and posted on the platform yet ")
    @GetMapping("/make/{ownerId}")
    @Secured("ROLE_OWNER")
    public String showApplicationForm(@PathVariable int ownerId, Model model) {
        Owner owner = ownerService.getOwnerById(ownerId);
        List<Property> filteredProperties = propertyService.getPropertiesByOwnerId(ownerId);
        model.addAttribute("owner", owner);
        model.addAttribute("properties", filteredProperties);
        model.addAttribute("app", new OwnerApplication());
        return "applicationOwner/applicationForm";
    }
    @Operation(summary = "Submit owner application", description = "Submit a new application for the specified owner and property.")
    @PostMapping("/submit")
    @Secured("ROLE_OWNER")
    public String submitApplication(@ModelAttribute("app") OwnerApplication application, @RequestParam("ownerId") int ownerId,Model model) {
        Owner owner = ownerService.getOwnerById(ownerId);
        application.setOwner(owner);
        Property property = propertyService.getPropertyById(application.getProperty().getId());
        ownerApplicationService.saveOwnerApplication(application, property);
        List<Property> ownerProperties =propertyService.getAllPropertiesByOwnerId(ownerId);
        model.addAttribute("owner", owner);
        model.addAttribute("ownerProperties", ownerProperties);
        return "owner/ownerProperties";
    }

    @Operation(summary = "Change application status",
            description = "Loads the form to change the status of an application(accept/decline) to Admin users" +
                    "If accept is selected then the property is posted and available for everyone to see. " +
                    "If decline is selected then the property stays visible only to the owner.")
    @Secured("ROLE_ADMIN")
    @GetMapping("/change/appStatus/{appId}")
    public String changeStatusApplication(@PathVariable int appId, Model model) {
        OwnerApplication ownerApplication = ownerApplicationService.getOwnerApplicationById(appId);
        model.addAttribute("app", ownerApplication);
        return "applicationOwner/applicationStatusChange";
    }
    @Operation(summary = "Confirm change of application status", description = "Process the status change for an owner application, returns the now changed status of the property along the other property application requests")
    @Secured("ROLE_ADMIN")
    @PostMapping("/change/appStatus/{appId}")
    public String confirmChangeStatusApplication(@ModelAttribute("application") OwnerApplication application,@PathVariable int appId,  @RequestParam("action") String action,Model model) {
        ownerApplicationService.processApplicationAction(appId, action);
        model.addAttribute("ownerApplications", ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }



}
