package com.zhiend.photo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotoService extends IService<Photo> {

    boolean uploadPhoto(MultipartFile file, Long userId);

    boolean downloadPhoto(Long photoId);

    List<Photo> getPhotosByUser(Long userId);

    void deleteExpiredPhotos();
}
