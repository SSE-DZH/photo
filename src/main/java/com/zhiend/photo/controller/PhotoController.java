package com.zhiend.photo.controller;

import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private IPhotoService photoService;

    @PostMapping("/upload")
    public Result<Long> uploadPhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return Result.success(photoService.uploadPhoto(file, userId));
    }

    @GetMapping("/download/{photoId}")
    public void downloadPhoto(@PathVariable Long photoId, HttpServletResponse response) {
        boolean success = photoService.downloadPhoto(photoId, response);
        if (!success) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    public Result<List<Photo>> getPhotosByUser(@PathVariable Long userId) {
        List<Photo> photos = photoService.getPhotosByUser(userId);
        return Result.success(photos);
    }
}
