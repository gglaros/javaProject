package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.OwnerApplication;
import hua.project.Entities.Property;
import hua.project.Entities.Status;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/OwnerApplications")
public class OwnerApplicationController {

    private final OwnerApplicationService ownerApplicationService;
    private final OwnerService ownerService;
    private final PropertyService propertyService;

    public OwnerApplicationController(OwnerApplicationService ownerApplicationService, OwnerService ownerService, PropertyService propertyService) {
        this.ownerApplicationService = ownerApplicationService;
        this.ownerService = ownerService;
        this.propertyService = propertyService;
    }

    @GetMapping("")
    public String showApplications(Model model) {
        model.addAttribute("applications",ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }


    @GetMapping("/make/{ownerId}")
    public String showApplicationForm(@PathVariable int ownerId, Model model) {
        Owner owner = ownerService.getOwnerById(ownerId);
        List<Property> filteredProperties = propertyService.getPropertiesByOwnerId(ownerId);
        model.addAttribute("owner", owner);
        model.addAttribute("properties", filteredProperties);
        model.addAttribute("app", new OwnerApplication());
        return "applicationOwner/applicationForm";
    }


    @PostMapping("/submit")
    public String submitApplication(@ModelAttribute("app") OwnerApplication application, @RequestParam("ownerId") int ownerId,Model model) {
        Owner owner = ownerService.getOwnerById(ownerId);
        application.setOwner(owner);
        Property property = propertyService.getPropertyById(application.getProperty().getId());
        ownerApplicationService.saveOwnerApplication(application, property);
        model.addAttribute("applications", ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/change/appStatus/{appId}")
    public String changeStatusApplication(@PathVariable int appId, Model model) {
        OwnerApplication ownerApplication = ownerApplicationService.getOwnerApplicationById(appId);
        model.addAttribute("app", ownerApplication);
        return "applicationOwner/applicationStatusChange";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/change/appStatus/{appId}")
    public String confirmChangeStatusApplication(@ModelAttribute("application") OwnerApplication application,@PathVariable int appId,  @RequestParam("action") String action,Model model) {
        ownerApplicationService.processApplicationAction(appId, action);
        model.addAttribute("applications", ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }



}
