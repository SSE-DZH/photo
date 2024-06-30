package com.zhiend.photo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
 
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;
 
}