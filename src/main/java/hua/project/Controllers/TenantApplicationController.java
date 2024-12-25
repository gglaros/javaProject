package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Service.OwnerService;
import hua.project.Service.PropertyService;
import hua.project.Service.TenantApplicationService;
import hua.project.Service.TenantService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("tenantApplications")
public class TenantApplicationController {

    private final TenantService tenantService;
    private final PropertyService propertyService;
    private final OwnerService ownerService;
    private final TenantApplicationService tenantApplicationService;

    public TenantApplicationController(TenantService tenantService, PropertyService propertyService, OwnerService ownerService, TenantApplicationService tenantApplicationService) {
        this.tenantService = tenantService;
        this.propertyService = propertyService;
        this.ownerService = ownerService;
        this.tenantApplicationService = tenantApplicationService;
    }


    @GetMapping("/make/{tenantId}")
    public String showApplicationForm(@PathVariable int tenantId, Model model) {
       Tenant tenant = tenantService.getTenantById(tenantId);
        List<Property> filteredProperties = tenantApplicationService.getPropertiesByOnEyeStatusAndNoApplication(tenantId);
        TenantApplication tenantApplication = new TenantApplication();
       tenantApplication.setTenant(tenant);
     model.addAttribute("tenantApplication", tenantApplication);
     model.addAttribute("properties", filteredProperties);
        return "applicationTenant/tenantApplicationForm";
    }


    @PostMapping("/submit")
    public String submitApplication(@RequestParam("propertyId") int propertyId, @ModelAttribute("tenantApplication") TenantApplication tenantApplication, Model model) {
        Property property = propertyService.getPropertyById(propertyId);
        Owner owner = ownerService.getOwnerById(property.getOwner().getId());
        tenantApplicationService.save(tenantApplication,property,owner);
        System.out.println(List.of(tenantApplication));
        List<TenantApplication> tenantApplications = tenantApplicationService.findAll();
        model.addAttribute("tenantApplications", tenantApplications);
        return "applicationTenant/tenantApplications";
    }


    @GetMapping("/change/appStatus/{appId}")
    public String changeStatusApplication(@PathVariable int appId, Model model) {
        TenantApplication tenantApplication =tenantApplicationService.getTenantApplicationById(appId);
        model.addAttribute("app", tenantApplication);
        return "applicationTenant/tenantAppChangeStatus";
    }

    @Secured("ROLE_TENANT")
    @PostMapping("/change/appStatus/{appId}")
    public String confirmChangeStatusApplication(@ModelAttribute("application") OwnerApplication application,@PathVariable int appId,  @RequestParam("action") String action,Model model) {
        tenantApplicationService.processTenantApplicationAction(appId, action);
        model.addAttribute("tenantApplications", tenantApplicationService.findAll());
        return "applicationTenant/tenantApplications";
    }



}