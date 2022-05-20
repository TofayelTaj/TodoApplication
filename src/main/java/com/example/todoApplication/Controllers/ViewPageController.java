package com.example.todoApplication.Controllers;

import com.example.todoApplication.Entitys.Supervisor;
import com.example.todoApplication.Repositories.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewPageController {

    @Autowired
    private SupervisorRepository supervisorRepository;

//........SUPERVISOR CONTROLLERS.............

//    @GetMapping("/supervisor/login")
//    public String getSupervisorLoginPage(){
//        return "SupervisorTemplates/login";
//    }

    @GetMapping("/supervisor/signup")
    public String getSupervisorSignupPage(){
        return "SupervisorTemplates/signup";
    }

//...........USER CONTROLLERS..............

//    @GetMapping("/user/login")
//    public String getUserLoginPage(){
//        return "UserTemplates/login";
//    }

    @GetMapping("/user/signup")
    public String getUserSignupPage(Model model){
        List<Supervisor> allSupervisor = supervisorRepository.findAll();
        model.addAttribute("allSupervisor", allSupervisor);

        return "UserTemplates/signup";
    }





}
