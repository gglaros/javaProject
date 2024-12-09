package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.OwnerApplication;
import hua.project.Entities.Property;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


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



    @GetMapping("/make/{propertyId}/{ownerId}")
    public String showApplicationForm(@PathVariable int propertyId, @PathVariable int ownerId, Model model) {
        Owner owner = ownerService.getOwnerById(ownerId);
        System.out.println(owner.getFirstName());

        Property property = propertyService.getPropertyById(propertyId);
        System.out.println(property.getAddress());

        OwnerApplication application = new OwnerApplication();
//        application.setOwner(owner);
//        application.setProperty(property);
        ownerApplicationService.saveOwnerApplication(application,owner,property);
        System.out.println(application.getOwner().getFirstName());

        model.addAttribute("applications",ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }



//    @PostMapping("/submit")
//    public String submitApplication(@ModelAttribute("application") OwnerApplication application, Model model) {
//        ownerApplicationService.saveOwnerApplication(application);
//        System.out.println("onsumbit"+application.getOwner());
//
//        model.addAttribute("applications", ownerApplicationService.getOwnerApplications());
//        return "applicationOwner/applications";
//    }



}
