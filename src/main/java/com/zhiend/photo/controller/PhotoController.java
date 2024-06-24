package com.zhiend.photo.controller;


import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhiend
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private IPhotoService photoService;

    @PostMapping("/upload")
    public Result<String> uploadPhoto(@RequestBody Photo photo) {
        boolean success = photoService.save(photo);
        if (success) {
            return Result.success("Photo uploaded successfully");
        } else {
            return Result.error("Photo upload failed");
        }
    }

    @GetMapping("/{userId}")
    public Result<List<Photo>> getPhotosByUser(@PathVariable Long userId) {
        List<Photo> photos = photoService.getPhotosByUser(userId);
        return Result.success(photos);
    }
}
