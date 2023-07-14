package com.example.RestaurantManagementServiceAPI.controller;

import com.example.RestaurantManagementServiceAPI.model.User;
import com.example.RestaurantManagementServiceAPI.model.UserType;
import com.example.RestaurantManagementServiceAPI.model.dto.SignInInput;
import com.example.RestaurantManagementServiceAPI.model.dto.SignUpOutput;
import com.example.RestaurantManagementServiceAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("user/signUp")
    public SignUpOutput signUpUser(@RequestBody @Valid User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.sigInUser(signInInput);
    }
    @GetMapping("users")
    List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @DeleteMapping("user/{Id}")
    public void deleteUser(@PathVariable Integer Id)
    {
        userService.deleteUser(Id);
    }


}
