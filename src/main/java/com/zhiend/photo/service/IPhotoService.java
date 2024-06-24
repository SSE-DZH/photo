package com.zhiend.photo.service;

import com.zhiend.photo.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
public interface IPhotoService extends IService<Photo> {

    List<Photo> getPhotosByUser(Long userId);
}
