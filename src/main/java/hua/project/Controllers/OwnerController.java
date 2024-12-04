package hua.project.Controllers;


import hua.project.Entities.Owner;
import org.springframework.ui.Model;
import hua.project.Service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("owner")
public class OwnerController {

OwnerService ownerService;

public OwnerController(OwnerService ownerService) {
    this.ownerService=ownerService;
}

@GetMapping("")
public String showOwners(Model model) {
    model.addAttribute("owners", ownerService.getAllOwners());
    return "owner/owners";
}

@GetMapping("/{id}")
public String showOwnerId(Model model, @PathVariable Integer id) {
   model.addAttribute("owners", ownerService.getOwnerById(id));
    return "owner/owners";
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
        return "owner/owners";
    }




}
