package com.zhiend.photo.controller;


import com.zhiend.photo.dto.UserLoginDTO;
import com.zhiend.photo.dto.UserRegisterDTO;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        boolean success = userService.register(userRegisterDTO);
        if (success) {
            return Result.success("User registered successfully");
        } else {
            return Result.error("User registration failed");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        boolean success = userService.login(userLoginDTO.getPhone(), userLoginDTO.getPassword());
        if (success) {
            return Result.success("User logged in successfully");
        } else {
            return Result.error("Invalid phone number or password");
        }
    }
}
