package com.zhiend.photo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiend.photo.entity.Photo;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotoService extends IService<Photo> {

    Long uploadPhoto(MultipartFile file, Long userId);

    boolean downloadPhoto(Long photoId, HttpServletResponse response);

    List<Photo> getPhotosByUser(Long userId);

    void deleteExpiredPhotos();
}
