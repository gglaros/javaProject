package hua.project.Controllers;

import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("property")
public class propertyController {

    PropertyService propertyService;

    public propertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @GetMapping("")
    public String showProperties(Model model) {
        model.addAttribute("properties", propertyService.getAllProperty());
        return "property/propertyList";
    }

    @GetMapping("/{id}")
    public String showPropertyId(Model model, @PathVariable Integer id) {
        model.addAttribute("properties", propertyService.getPropertyById(id));
        return "property/propertyList";
    }



}
