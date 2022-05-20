package com.example.todoApplication.Controllers;

import com.example.todoApplication.Entitys.Status;
import com.example.todoApplication.Entitys.Supervisor;
import com.example.todoApplication.Entitys.Todo;
import com.example.todoApplication.Entitys.User;
import com.example.todoApplication.Repositories.SupervisorRepository;
import com.example.todoApplication.Repositories.TodoRepository;
import com.example.todoApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/dashboard")
    public String getDashboard(Principal principal, Model model, @RequestParam("date") Optional<String> date,
    @RequestParam("userId") Optional<Long> userId){


        long id =  supervisorRepository.getSupervisorByEmail(principal.getName()).getId();
        List<User> allUser = userRepository.getUsersIdBySupervisorId(id);
        List<Todo> allTodo = new ArrayList<>();
        String d =  date.orElse("");

        model.addAttribute("status", Status.values());


        model.addAttribute("allUser", allUser);

        if(!d.isEmpty()){
            for (User user : allUser) {
                allTodo.addAll(todoRepository.findByCreatedTime(d, user.getId()));
            }
        }else if(!userId.isEmpty()){
            allTodo.addAll(todoRepository.findByUserId(userId.get()));
        }else{
            for (User user : allUser){
                allTodo.addAll(todoRepository.findByUserId(user.getId()));
            }
        }

        model.addAttribute("allTodo", allTodo);

        return "SupervisorTemplates/dashboard";
    }


    @PostMapping("/signup")
    public String signup(@ModelAttribute Supervisor supervisor, HttpServletRequest request, Model model){

        supervisor.setPassword(passwordEncoder.encode(supervisor.getPassword()));
        try{
            supervisorRepository.save(supervisor);
        }catch (Exception e){
            model.addAttribute("error", "Email must be unique");
            return "SupervisorTemplates/signup";
        }

        return "redirect:" + request.getHeader("Referer");
    }


    @GetMapping("/status")
    public String changeTodoStatus(@RequestParam("status") String status, @RequestParam("todoId") String todoId){
        System.out.println(".......................................................");
        System.out.println(status.toString());
        System.out.println(todoId);
        Todo todo = todoRepository.findById(Long.parseLong(todoId));
        if(status.equalsIgnoreCase(Status.PENDING.toString())){
            todo.setStatus(Status.PENDING);
        }else if(status.equalsIgnoreCase(Status.PROCESSING.toString())){
            todo.setStatus(Status.PROCESSING);
        }else if(status.equalsIgnoreCase(Status.COMPLETED.toString())){
            todo.setStatus(Status.COMPLETED);
        }
        todoRepository.save(todo);
       return "redirect:/supervisor/dashboard";

    }



}
