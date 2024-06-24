package com.zhiend.photo.config;

import com.zhiend.photo.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TaskConfig {

    @Autowired
    private IPhotoService photoService;

    // 每天凌晨执行一次，删除过期照片
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredPhotos() {
        photoService.deleteExpiredPhotos();
    }
}
