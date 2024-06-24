package com.zhiend.photo.service;

import com.zhiend.photo.dto.UserRegisterDTO;
import com.zhiend.photo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
public interface IUserService extends IService<User> {

    boolean register(UserRegisterDTO userRegisterDTO);
    boolean login(String phone, String password);
}
