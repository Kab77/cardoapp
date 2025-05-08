package com.example.demo.controller;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @GetMapping
    public List<Task> getTasks(Authentication auth) {
        String username = auth.getName();
        User user = userService.getByUsername(username);
        return taskService.findByUser(user.getId());
    }
    @PostMapping
    public Task createTask(@RequestBody Task task, Authentication auth) {
        String username = auth.getName();
        User user = userService.getByUsername(username);
        task.setUser(user);
        return taskService.save(task);
    }
} 