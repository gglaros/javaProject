package hua.project.Controllers;


import hua.project.Entities.Owner;
import hua.project.Entities.Property;
import hua.project.Service.PropertyService;
import org.springframework.ui.Model;
import hua.project.Service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("owner")
public class OwnerController {

    OwnerService ownerService;

    PropertyService propertyService;

    public OwnerController(OwnerService ownerService, PropertyService propertyService) {
        this.ownerService = ownerService;
        this.propertyService = propertyService;
    }

    @GetMapping("")
    public String showOwners(Model model) {
        model.addAttribute("owners", ownerService.getAllOwners());
        return "owner/ownersList";
    }

    @GetMapping("/{id}")
    public String showOwnerId(Model model, @PathVariable Integer id) {
        model.addAttribute("owners", ownerService.getOwnerById(id));
        return "owner/ownersList";
    }

    @GetMapping("/new")
    public String addOwner(Model model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "owner/Owner";
    }


    @PostMapping("/new")
    public String saveOwner(@ModelAttribute("owner") Owner owner, Model model) {
        ownerService.saveOwner(owner);
        model.addAttribute("owners", ownerService.getAllOwners()  );
        return "owner/ownersList";
    }


    @GetMapping("/make/property/{id}")
    public String addProperty(@PathVariable int id, Model model) {
        Property property = new Property();
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("property", property);
        model.addAttribute("owner", owner);
        return "property/propertyForm";
    }



    @PostMapping("/make/property/{id}")
    public String saveProperty(@PathVariable int id, @ModelAttribute("property") Property property, Model model) {
        Owner owner = ownerService.getOwnerById(id);
        ownerService.savePropertyToOwner(owner,property);
        model.addAttribute("properties", propertyService.getAllProperty());
        return "property/propertyList";
    }

@GetMapping("show/properties/{id}")
public String showOwnerProperties(Model model, @PathVariable int id) {
        List<Property> ownerProperties =propertyService.getPropertiesByOwnersId(id);
        model.addAttribute("ownerProperties", ownerProperties);
        return "owner/ownerProperties";
}




}
