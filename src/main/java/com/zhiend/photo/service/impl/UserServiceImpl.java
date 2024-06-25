package com.zhiend.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhiend.photo.dto.UserRegisterDTO;
import com.zhiend.photo.entity.User;
import com.zhiend.photo.mapper.UserMapper;
import com.zhiend.photo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiend.photo.vo.LoginVO;
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
    public Long register(UserRegisterDTO userRegisterDTO) {
        User existingUser = this.getOne(new QueryWrapper<User>().eq("phone", userRegisterDTO.getPhone()));
        // 如果手机号已存在，返回 null 表示注册失败
        if (existingUser != null) {
            return null;
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        this.save(user);
        return user.getId();
    }

    @Override
    public LoginVO login(String phone, String password) {
        User existingUser = this.getOne(new QueryWrapper<User>().eq("phone", phone).eq("password", password));
        if (existingUser == null) {
            return null;
        }
        return new LoginVO(existingUser.getId(), existingUser.getUsername());
    }
}
