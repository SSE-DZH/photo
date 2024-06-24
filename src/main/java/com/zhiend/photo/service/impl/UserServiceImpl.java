package com.zhiend.photo.service.impl;

import com.zhiend.photo.entity.User;
import com.zhiend.photo.mapper.UserMapper;
import com.zhiend.photo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
