package com.zhiend.photo.controller;

import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.result.Result;
import com.zhiend.photo.service.IPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = "*")
@Api(tags = "图片上传接口")
public class PhotoController {

    @Autowired
    private IPhotoService photoService;

    /**
     * 上传图片
     * @param file
     * @param userId
     * @return
     */
    @PostMapping("/upload")
    public Result<Long> uploadPhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return Result.success(photoService.uploadPhoto(file, userId));
    }

    /**
     * 下载图片
     * @param photoId
     * @param response
     */
    @ApiOperation(value = "下载图片")
    @GetMapping("/download/{photoId}")
    public void downloadPhoto(@PathVariable Long photoId, HttpServletResponse response) {
        boolean success = photoService.downloadPhoto(photoId, response);
        if (!success) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * 获取用户图片
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户图片")
    @GetMapping("/{userId}")
    public Result<List<Photo>> getPhotosByUser(@PathVariable Long userId) {
        List<Photo> photos = photoService.getPhotosByUser(userId);
        return Result.success(photos);
    }
}
