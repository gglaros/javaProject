package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.OwnerApplication;
import hua.project.Entities.Property;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "application/applications";
    }

    @GetMapping("/make/{propertyId}/{ownerId}")
    public String showApplicationForm(@PathVariable int propertyId, @PathVariable int ownerId, Model model) {
        // Έλεγχος αν ο ownerId είναι έγκυρος
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Invalid owner ID");
        }

        // Ανάκτηση του ακινήτου
        Property property = propertyService.getPropertyById(propertyId);
        if (property == null) {
            throw new IllegalArgumentException("Invalid property ID");
        }
System.out.println("OWNERID = " + ownerId);
System.out.println("PROPERTYID = " + propertyId);
        // Δημιουργία νέας αίτησης
        OwnerApplication application = new OwnerApplication();
        application.setOwner(owner);
        application.setProperty(property);

        // Προσθήκη στο μοντέλο
        model.addAttribute("application", application);

        return "application/applicationForm"; // Το όνομα του Thymeleaf template
    }


    // POST: Υποβολή της αίτησης
//    @PostMapping("/submit")
//    public String submitApplication(@ModelAttribute("application") OwnerApplication application, Principal principal) {
//        // Ορισμός της κατάστασης της αίτησης ως PENDING
//        application.setStatus(ApplicationStatus.PENDING);
//
//        // Αποθήκευση της αίτησης
//        ownerApplicationService.save(application);
//
//        return "redirect:/owner-applications/success"; // Ανακατεύθυνση σε σελίδα επιτυχίας
//    }

}
