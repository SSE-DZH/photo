package com.zhiend.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.mapper.PhotoMapper;
import com.zhiend.photo.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {
    @Override
    public List<Photo> getPhotosByUser(Long userId) {
        return this.list(new QueryWrapper<Photo>().eq("user_id", userId));
    }
}
