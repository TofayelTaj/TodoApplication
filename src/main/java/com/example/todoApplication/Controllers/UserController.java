package com.example.todoApplication.Controllers;

import com.example.todoApplication.Entitys.Supervisor;
import com.example.todoApplication.Entitys.Todo;
import com.example.todoApplication.Entitys.User;
import com.example.todoApplication.Repositories.SupervisorRepository;
import com.example.todoApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TodoController todoController;
    @Autowired
    private SupervisorRepository supervisorRepository;

    @GetMapping("/dashboard")
    public String getDashboard(Model model, Principal principal){
        List<Todo> todoList = todoController.getAllTodos(principal, model);
        model.addAttribute("listOfTodo", todoList);
        return "UserTemplates/dashboard";
    }



    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, HttpServletRequest request, Model model){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try{
        userRepository.save(user);

        }catch (Exception e){
            model.addAttribute("error", "Email must be unique");
            List<Supervisor> allSupervisor = supervisorRepository.findAll();
            model.addAttribute("allSupervisor", allSupervisor);

            return "UserTemplates/signup";
        }
        return "redirect:" + request.getHeader("Referer");
    }



}
