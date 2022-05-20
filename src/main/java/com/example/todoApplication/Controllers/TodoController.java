package com.example.todoApplication.Controllers;

import com.example.todoApplication.Entitys.Status;
import com.example.todoApplication.Entitys.Todo;
import com.example.todoApplication.Entitys.User;
import com.example.todoApplication.Repositories.TodoRepository;
import com.example.todoApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;



@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/create")
    public String getTodoCreatePage(){
        return "TodoTemplates/createTodo";
    }

    @GetMapping("/update/{id}")
    public String getTodoUpdatePage(@PathVariable long id, Model model, Principal principal){

        Todo  todo = todoRepository.findById(id);
        if(todo.getUser().getEmail().equals(principal.getName())){
            model.addAttribute("todo", todo);
        }else{
            return "redirect:/user/dashboard" ;
        }
        return "TodoTemplates/update";
    }
    @PostMapping("/update")
    public String updateTodo(@ModelAttribute Todo todo){

        Todo oldTodo = todoRepository.getById(todo.getId());
        oldTodo.setName(todo.getName());
        oldTodo.setDescription(todo.getDescription());
        todoRepository.save(oldTodo);
        return "redirect:/user/dashboard";
    }

    @PostMapping("/save")
    public String saveTodo(@ModelAttribute Todo todo, Principal principal){
        todo.setCreatedTime(LocalDate.now());
        todo.setStatus(Status.PENDING);
        String authUserName =  principal.getName();
        User user = userRepository.getUserByEmail(authUserName);
        todo.setUser(user);
        todoRepository.save(todo);
        return "redirect:/user/dashboard";
    }


    public List<Todo> getAllTodos(Principal principal, Model model){
        String authUserName =  principal.getName();
        User user = userRepository.getUserByEmail(authUserName);
        List<Todo> todoList = todoRepository.findByUserId(user.getId());
        return todoList;
    }



}
