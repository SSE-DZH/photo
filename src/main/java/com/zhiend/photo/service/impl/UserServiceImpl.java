package com.zhiend.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhiend.photo.dto.UserRegisterDTO;
import com.zhiend.photo.entity.User;
import com.zhiend.photo.mapper.UserMapper;
import com.zhiend.photo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        return this.save(user);
    }

    @Override
    public boolean login(String phone, String password) {
        User existingUser = this.getOne(new QueryWrapper<User>().eq("phone", phone).eq("password", password));
        return existingUser != null;
    }
}
