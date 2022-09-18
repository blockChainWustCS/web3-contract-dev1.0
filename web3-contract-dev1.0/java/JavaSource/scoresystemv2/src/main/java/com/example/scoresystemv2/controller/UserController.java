package com.example.scoresystemv2.controller;

import com.example.scoresystemv2.bean.User;
import com.example.scoresystemv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public User userSelect(@RequestParam("address")String address){
        return userService.getFromAddress(address);
    }

    @PostMapping("")
    public void addUser(@RequestBody User user) {
        userService.register(user);
    }

    @PutMapping("")
    public void updataUser(@RequestBody User user){
        userService.updata(user);
    }

}
