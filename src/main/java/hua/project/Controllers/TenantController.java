package hua.project.Controllers;

import hua.project.Repository.TenantRepository;
import hua.project.Service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tenant")
public class TenantController {

    TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }
}
