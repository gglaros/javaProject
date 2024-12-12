package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.OwnerApplication;
import hua.project.Entities.Property;
import hua.project.Entities.Status;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/applications")
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

        OwnerApplication application = new OwnerApplication();
        application.setOwner(owner);
        System.out.println("kalimera "+ owner);
        model.addAttribute("properties", filteredProperties );
        model.addAttribute("app", application);
        return "applicationOwner/applicationForm";
    }


    @PostMapping("/submit")
    public String submitApplication(@ModelAttribute("application") OwnerApplication application, Model model) {
        Property property = propertyService.getPropertyById(application.getProperty().getId());
        property.setStatus("waiting");
        System.out.println("on post submit method property id = "+ property.getId());
        ownerApplicationService.saveOwnerApplication(application);


        System.out.println("on post submit method applications = "+ ownerApplicationService.getOwnerApplications());

        model.addAttribute("applications", ownerApplicationService.getOwnerApplications());

        return "applicationOwner/applications";
    }

}
