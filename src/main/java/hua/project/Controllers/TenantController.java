package hua.project.Controllers;

import hua.project.Entities.*;
import hua.project.Service.OwnerService;
import hua.project.Service.TenantApplicationService;
import hua.project.Service.TenantService;
import hua.project.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("tenant")
public class TenantController {

    TenantService tenantService;
    TenantApplicationService tenantApplicationService;
    UserService userService;
    OwnerService ownerService;

    public TenantController(TenantService tenantService, TenantApplicationService tenantApplicationService, UserService userService, OwnerService ownerService) {
        this.tenantService = tenantService;
        this.tenantApplicationService = tenantApplicationService;
        this.userService = userService;
        this.ownerService = ownerService;
    }
    @Operation(
            summary = "Get list of all tenants",
            description = "Fetches the list of all tenants in the system. Accessible by admins only.",
            tags = {"Tenant Management"}
    )
    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showTenants(Model model) {
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenant/tenantList";
    }
    @Operation(
            summary = "View tenant's rental applications",
            description = "Fetches the rental applications submitted by the logged-in tenant.",
            tags = {"Tenant Applications"}
    )
    @GetMapping("/rentalRequests")
    public String showTenantApplicationById(Authentication authentication, Model model) {
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
            List<TenantApplication> tenantApplicationsByTenantId = tenantApplicationService.ApplicationsByTenantId(id);
            model.addAttribute("tenantApplications", tenantApplicationsByTenantId);
            return "applicationTenant/tenantApplications";
        } else {
            return "tenant/unvalidated";
        }
    }

    @Operation(
            summary = "View all tenant applications",
            description = "Fetches the list of all tenant applications. This is only accessible to admins.",
            tags = {"Tenant Applications"}
    )

    @Secured("ROLE_ADMIN")
    @GetMapping("/all/applications")
    public String showAllTenantApplications(Model model) {
        List<TenantApplication> tenantApplications=tenantApplicationService.findAll();
        model.addAttribute("tenantApplications", tenantApplications);
        return "applicationTenant/tenantApplications";
    }

    @Operation(
            summary = "View tenant profile",
            description = "Displays the profile of the logged-in tenant, or creates a new profile if it doesn't exist.",
            tags = {"Tenant Profile"}
    )

    @GetMapping("/profile")
    public String viewProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {throw new RuntimeException("User not found");}
        Tenant existTenant = tenantService.findByUser(user);

        if (existTenant == null) {
            Tenant tenant = new Tenant();
            model.addAttribute("tenant", tenant);
            model.addAttribute("user", user);
            return "tenant/tenant";
        }
        model.addAttribute("tenant", existTenant);
        return "tenant/tenantProfile";
    }

    @Operation(
            summary = "Create or update tenant profile",
            description = "Creates a new tenant profile or updates the existing one using the provided details.",
            tags = {"Tenant Profile"}
    )
    @PostMapping("/new")
    public String saveOwner(@Valid @ModelAttribute("tenant") Tenant tenant, BindingResult result,@RequestParam("userId") int userId, Model model) {
        User user = userService.getUserById(userId);
        if (result.hasErrors()) {
            model.addAttribute("tenant", tenant);
            return "tenant/tenant";
        }
        if (user == null) {throw new RuntimeException("User not found");}

        tenantService.saveTenant(tenant,user, user.getEmail());
        tenantService.changeTenantStatus(tenant);
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenant/tenantProfile";
    }

    @Operation(summary = "View tenant validation status change form",
            description = "Allows an admin to view a tenant's details before updating their validation status.",
            tags = {"Tenant Management"})
    @Secured("ROLE_ADMIN")
    @GetMapping("/change/valStatus/{tenantId}")
    public String changeValidationStatus(@PathVariable int tenantId, Model model) {
        Tenant tenant =tenantService.getTenantById(tenantId);
        model.addAttribute("tenant", tenant);
        return "tenant/tenantValidation";
    }

    @Operation(summary = "Update tenant validation status",
            description = "Allows an admin to change the validation status of a tenant (e.g., validated or not)," +
                    "tags = {Tenant Management}")
    @Secured("ROLE_ADMIN")
    @PostMapping("/change/valStatus/{tenantId}")
    public String confirmChangeValidationStatus(@ModelAttribute("tenant") Tenant tenant, @PathVariable int tenantId, @RequestParam("action") String action, Model model) {
        tenantService.processTenantValidationStatus(tenantId, action);
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenant/tenantList";
    }

}
