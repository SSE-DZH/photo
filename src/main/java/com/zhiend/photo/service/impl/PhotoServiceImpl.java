package com.zhiend.photo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiend.photo.entity.Photo;
import com.zhiend.photo.mapper.PhotoMapper;
import com.zhiend.photo.service.IPhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    @Transactional
    public Long uploadPhoto(MultipartFile file, Long userId) {
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String fileName =  UUID.randomUUID().toString() + extension;
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            Photo photo = new Photo();
            photo.setUserId(userId);
            photo.setFilename(fileName);
            photo.setStoragePath(filePath.toString());
            photo.setUploadTime(LocalDateTime.now());
            this.save(photo);

            return photo.getId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean downloadPhoto(Long photoId, HttpServletResponse response) {
        Photo photo = this.getById(photoId);
        if (photo == null) {
            return false;
        }

        File file = new File(photo.getStoragePath());
        if (!file.exists()) {
            return false;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + photo.getFilename() + "\"");

        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Photo> getPhotosByUser(Long userId) {
        return this.list(new QueryWrapper<Photo>().eq("user_id", userId));
    }

    @Override
    @Transactional
    public void deleteExpiredPhotos() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.lt("upload_time", oneDayAgo);

        List<Photo> expiredPhotos = this.list(wrapper);
        List<String> storagePaths = expiredPhotos.stream().map(Photo::getStoragePath).collect(Collectors.toList());

        // 删除数据库中的记录
        this.remove(wrapper);

        // 删除文件系统中的文件
        storagePaths.forEach(path -> {
            try {
                Files.deleteIfExists(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
                // 处理文件删除失败的情况
            }
        });
    }
}
