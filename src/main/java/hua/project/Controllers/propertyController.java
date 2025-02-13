package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("property")
public class propertyController {

    private PropertyService propertyService;
    private  OwnerApplicationService ownerApplicationService;

    public propertyController(PropertyService propertyService, OwnerApplicationService ownerApplicationService) {
        this.propertyService = propertyService;
        this.ownerApplicationService = ownerApplicationService;
    }
    @Operation(
            summary = "Get all properties",
            description = "Fetches a list of all properties available in the system. Accessible only to admins.",
            tags = {"Property"}
    )
    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public String showProperties(Model model) {
        List<Property> properties = propertyService.getAllProperty();
        properties.forEach(property -> System.out.println(property.getCity()));
        model.addAttribute("properties", propertyService.getAllProperty());
        return "property/propertyList";
    }
    @Operation(
            summary = "Get property by ID",
            description = "Fetches details of a specific property based on its ID.",
            tags = {"Property"}
    )
    @GetMapping("/{id}")
    public String showPropertyId(Model model, @PathVariable Integer id) {
        model.addAttribute("properties", propertyService.getPropertyById(id));
        return "property/propertyList";
    }



}
