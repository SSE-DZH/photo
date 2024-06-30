package com.zhiend.photo.controller;


import com.zhiend.photo.dto.UserLoginDTO;
import com.zhiend.photo.dto.UserRegisterDTO;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IUserService;
import com.zhiend.photo.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "*")
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 处理用户注册请求。
     * 通过@RequestBody注解，将HTTP请求体中的数据绑定到userRegisterDTO对象上。
     * 方法返回一个Result对象，其中包含注册操作的结果。
     *
     * @param userRegisterDTO 包含用户注册信息的数据传输对象，包括用户名、密码和电话号码。
     * @return 如果注册成功，返回包含新用户ID的Result对象；如果注册失败或信息无效，返回一个错误信息的Result对象。
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result<Long> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        // 检查注册信息是否完整
        if (userRegisterDTO.getUsername() == null || userRegisterDTO.getPassword() == null || userRegisterDTO.getPhone() == null) {
            return Result.error("Invalid register information");
        }

        // 尝试注册用户，如果注册失败（例如，用户名已存在），则返回错误信息
        Long userId = userService.register(userRegisterDTO);
        if (userId == null) {
            return Result.error("Registration failed, phoneNumber already exists");
        }

        // 注册成功，返回新用户的ID
        return Result.success(userId);
    }

    /**
     * 处理用户登录请求。
     * 通过@RequestBody注解，将HTTP请求体中的数据绑定到userLoginDTO对象上。
     * 方法返回一个Result对象，其中包含登录操作的结果。
     *
     * @param userLoginDTO 包含用户登录信息的数据传输对象，包括电话号码和密码。
     * @return 如果登录成功，返回包含登录信息的LoginVO对象；如果登录失败或信息无效，返回一个错误信息的Result对象。
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        if (userLoginDTO.getPhone() == null || userLoginDTO.getPassword() == null) {
            return Result.error("Invalid login information");
        }
        if (userService.login(userLoginDTO.getPhone(), userLoginDTO.getPassword()) == null) {
            return Result.error("Invalid login information");
        }
        return Result.success(userService.login(userLoginDTO.getPhone(), userLoginDTO.getPassword()));
    }
}
