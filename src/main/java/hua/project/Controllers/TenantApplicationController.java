package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
    UserService userService;
    public TenantApplicationController(TenantService tenantService, PropertyService propertyService, OwnerService ownerService, TenantApplicationService tenantApplicationService, UserService userService) {
        this.tenantService = tenantService;
        this.propertyService = propertyService;
        this.ownerService = ownerService;
        this.tenantApplicationService = tenantApplicationService;
        this.userService = userService;
    }
    @Operation(
            summary = "View available properties for tenant application",
            description = "Fetches the list of properties available for application based on the tenant's status. The tenant must be logged in.",
            tags = {"Tenant Applications"}
    )
    @GetMapping("/viewProperties")
    public String showApplicationForm(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Tenant existTenant = tenantService.findByUser(user);

        if (existTenant == null) {
            Tenant tenant = new Tenant();
            model.addAttribute("tenant", tenant);
            model.addAttribute("user", user);
            return "tenant/tenant";
        }
        if (existTenant.getValidation()==Validation.VALIDATED) {
            int id = existTenant.getId();
            List<Property> filteredProperties = tenantApplicationService.getPropertiesByOnEyeStatusAndNoApplication(id);
            TenantApplication tenantApplication = new TenantApplication();
            tenantApplication.setTenant(existTenant);
            model.addAttribute("tenantApplication", tenantApplication);
            model.addAttribute("properties", filteredProperties);
            return "applicationTenant/tenantApplicationForm";
        } else {
            return "tenant/unvalidated";
        }
    }

    @Operation(
            summary = "Submit tenant application",
            description = "Allows the tenant to submit an application for a property they are interested in.",
            tags = {"Tenant Applications"}
    )
    @PostMapping("/submit")
    public String submitApplication(@RequestParam("propertyId") int propertyId,@RequestParam(value = "visitChecked", required = false) String visitChecked,@RequestParam("tenant") int tenantId, @ModelAttribute("tenantApplication") TenantApplication tenantApplication, Model model) {
        Property property = propertyService.getPropertyById(propertyId);
        Owner owner = ownerService.getOwnerById(property.getOwner().getId());
        tenantApplicationService.save(tenantApplication,property,owner,visitChecked);
        List<TenantApplication> tenantApplications = tenantApplicationService.ApplicationsByTenantId(tenantId);
       // List<TenantApplication> tenantApplications = tenantApplicationService.findAll();
        model.addAttribute("tenantApplications", tenantApplications);
        return "applicationTenant/tenantApplications";
    }

    @Operation(
            summary = "Change application status",
            description = "Allows owners to change the status of a tenant's application.",
            tags = {"Tenant Applications"}
    )
    @Secured("ROLE_OWNER")
    @GetMapping("/change/appStatus/{appId}")
    public String changeStatusApplication(@PathVariable int appId, Model model) {
        TenantApplication tenantApplication =tenantApplicationService.getTenantApplicationById(appId);
        model.addAttribute("app", tenantApplication);
        return "applicationTenant/tenantAppChangeStatus";
    }
    @Operation(
            summary = "Confirm application status change",
            description = "Confirms the action taken on a tenant's application (approve/reject).",
            tags = {"Tenant Applications"}
    )
    @Secured("ROLE_OWNER")
    @PostMapping("/change/appStatus/{appId}")
    public String confirmChangeStatusApplication(@ModelAttribute("application") OwnerApplication application,@PathVariable int appId,  @RequestParam("action") String action,Model model) {
        tenantApplicationService.processTenantApplicationAction(appId, action);
        model.addAttribute("tenantApplications", tenantApplicationService.findAll());
        return "applicationTenant/tenantApplications";
    }



}