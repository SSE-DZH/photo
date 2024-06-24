package com.zhiend.photo.controller;

import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private IPhotoService photoService;

    @PostMapping("/upload")
    public Result<String> uploadPhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        boolean success = photoService.uploadPhoto(file, userId);
        if (success) {
            return Result.success("Photo uploaded successfully");
        } else {
            return Result.error("Photo upload failed");
        }
    }

    @GetMapping("/download/{photoId}")
    public Result<String> downloadPhoto(@PathVariable Long photoId) {
        boolean success = photoService.downloadPhoto(photoId);
        if (success) {
            return Result.success("Photo downloaded successfully");
        } else {
            return Result.error("Photo download failed");
        }
    }

    @GetMapping("/{userId}")
    public Result<List<Photo>> getPhotosByUser(@PathVariable Long userId) {
        List<Photo> photos = photoService.getPhotosByUser(userId);
        return Result.success(photos);
    }
}
