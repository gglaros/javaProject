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
        List<Property> filteredProperties = propertyService.getPropertiesByOwnerId(ownerId); // owner's property and do not have application
        OwnerApplication application = new OwnerApplication();
        application.setOwner(owner);
        model.addAttribute("properties", filteredProperties );
        model.addAttribute("app", application);
        return "applicationOwner/applicationForm";
    }


    @PostMapping("/submit")
    public String submitApplication(@ModelAttribute("application") OwnerApplication application, Model model) {
        Property property = propertyService.getPropertyById(application.getProperty().getId());
        ownerApplicationService.saveOwnerApplication(application,property);
        model.addAttribute("applications", ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }



    @GetMapping("/change/appStatus/{appId}")
    public String changeStatusApplication(@PathVariable int appId, Model model) {
        OwnerApplication ownerApplication = ownerApplicationService.getOwnerApplicationById(appId);
        System.out.println(appId);
        Property property = ownerApplication.getProperty();
        System.out.println("city property app "+property.getCity());
        System.out.println("address property in app "+ownerApplication.getProperty().getAddress());
        System.out.println("owner's id in app "+ownerApplication.getOwner().getId());
        System.out.println("application id " + ownerApplication.getId());
     //  propertyService.saveStatusProperty(property);
       //  property.setStatus("on eye");
      //  ownerApplication.setStatus(Status.APPROVED);
    //    ownerApplicationService.changeOwnerApplication(ownerApplication);
        model.addAttribute("app", ownerApplication);
        return "applicationOwner/applicationStatusChange";
    }

    @PostMapping("/change/appStatus/{appId}")
    public String confirmChangeStatusApplication(@ModelAttribute("application") OwnerApplication application,@PathVariable int appId,  @RequestParam("action") String action,Model model) {
        ownerApplicationService.processApplicationAction(appId, action);

        // OwnerApplication ownerApplication = ownerApplicationService.getOwnerApplicationById(appId);
        System.out.println("Action: " + action);
      //  Property property = ownerApplication.getProperty();
      //  propertyService.saveStatusProperty(property);
       // ownerApplicationService.changeOwnerApplication(ownerApplication);

        model.addAttribute("applications", ownerApplicationService.getOwnerApplications());
        return "applicationOwner/applications";
    }



}
