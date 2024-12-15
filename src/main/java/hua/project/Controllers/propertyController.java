package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Service.OwnerApplicationService;
import hua.project.Service.PropertyService;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("")
    public String showProperties(Model model) {
        List<Property> properties = propertyService.getAllProperty();
        properties.forEach(property -> System.out.println(property.getCity() ) );
        model.addAttribute("properties", propertyService.getAllProperty());
        return "property/propertyList";
    }

    @GetMapping("/{id}")
    public String showPropertyId(Model model, @PathVariable Integer id) {
        model.addAttribute("properties", propertyService.getPropertyById(id));
        return "property/propertyList";
    }



}
