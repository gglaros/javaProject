package hua.project.Controllers;

import hua.project.Entities.Tenant;
import hua.project.Entities.TenantApplication;
import hua.project.Service.TenantApplicationService;
import hua.project.Service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("tenant")
public class TenantController {

    TenantService tenantService;
    TenantApplicationService tenantApplicationService;

    public TenantController(TenantService tenantService, TenantApplicationService tenantApplicationService) {
        this.tenantService = tenantService;
        this.tenantApplicationService = tenantApplicationService;
    }

    @GetMapping("")
    public String showTenants(Model model) {
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenant/tenantList";
    }

    @GetMapping("MyApplications/{tenantId}")
    public String showTenantApplicationById(@PathVariable int tenantId, Model model) {
        List<TenantApplication> tenantApplicationsByTenantId = tenantApplicationService.ApplicationsByTenantId(tenantId);
        model.addAttribute("tenantApplications", tenantApplicationsByTenantId);
        return "applicationTenant/tenantApplications";
    }

    @GetMapping("/all/applications")
    public String showAllTenantApplications(Model model) {
        List<TenantApplication> tenantApplications=tenantApplicationService.findAll();
        model.addAttribute("tenantApplications", tenantApplications);
        return "applicationTenant/tenantApplications";
    }


    @GetMapping("/new")
    public String addOwner(Model model) {
        Tenant tenant = new Tenant();
        model.addAttribute("tenant", tenant);
        return "tenant/tenant";
    }


    @PostMapping("/new")
    public String saveOwner(@ModelAttribute("tenant") Tenant tenant, Model model) {
        tenantService.saveTenant(tenant);
        model.addAttribute("tenants", tenantService.getAllTenants()  );
        return "tenant/tenantList";
    }



}
