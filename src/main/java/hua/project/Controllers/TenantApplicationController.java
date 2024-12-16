package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import hua.project.Service.TenantApplicationService;
import hua.project.Service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("tenantApplications")
public class TenantApplicationController {

    private  TenantService tenantService;
    private  PropertyService propertyService;
    private OwnerService ownerService;
    private TenantApplicationService tenantApplicationService;

    public TenantApplicationController(TenantService tenantService, PropertyService propertyService, OwnerService ownerService, TenantApplicationService tenantApplicationService) {
        this.tenantService = tenantService;
        this.propertyService = propertyService;
        this.ownerService = ownerService;
        this.tenantApplicationService = tenantApplicationService;
    }




    @GetMapping("/make/{tenantId}")
    public String showApplicationForm(@PathVariable int tenantId, Model model) {
       Tenant tenant = tenantService.getTenantById(tenantId);
        System.out.println("get tenant = "+tenant);
       List<Property> filteredProperties = propertyService.getPropertiesByOnEyeStatus(); // owner's property and do not have application
       System.out.println(filteredProperties.toString());
       TenantApplication tenantApplication = new TenantApplication();
       tenantApplication.setTenant(tenant);


     model.addAttribute("tenantApplication", tenantApplication);
     model.addAttribute("properties", filteredProperties);
        return "applicationTenant/tenantApplicationForm";
    }


    @PostMapping("/submit")
    public String submitApplication(@ModelAttribute("tenantApplication") TenantApplication tenantApplication, Model model) {
     //System.out.println("tenant" +  tenantApplication.getTenant());
     System.out.println("tenantApplication submit "+tenantApplication.toString());
     System.out.println("tenantApplication tenant submit "+tenantApplication.getTenant());
     Property property =propertyService.getPropertyById(tenantApplication.getProperty().getId());
    System.out.println("on submit == "+property.toString());
     tenantApplication.setProperty(property);
     Owner owner = ownerService.getOwnerById(tenantApplication.getProperty().getOwner().getId());
     System.out.println("on submit owner == "+owner);
     tenantApplication.setOwner(owner);
     tenantApplicationService.save(tenantApplication);
     List<TenantApplication> tenantApplications=tenantApplicationService.findAll();
     System.out.println(tenantApplications);
     model.addAttribute("tenantApplications", tenantApplications);
        return "applicationTenant/tenantApplications";
    }

}