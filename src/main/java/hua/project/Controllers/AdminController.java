//package hua.project.Controllers;
//
//import hua.project.Entities.Admin;
//import hua.project.Service.AdminService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/Admin")
//public class AdminController {
//
//    private AdminService adminService;
//
//
//    public AdminController(AdminService adminService) {
//        this.adminService = adminService;
//    }
//
//
//    @PostMapping("/new")
//    public void saveStudent(@RequestBody Admin admin) {
//        adminService.save(admin);
//    }
//
//
//    @GetMapping("/show")
//    public List<Admin> show(){
//        return adminService.getAllAdmins();
//    }
//
//
//
//
//}