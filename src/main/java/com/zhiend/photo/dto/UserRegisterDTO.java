package com.zhiend.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UserRegisterDTO
 * @Description
 * @Date 2024/6/24 21:37
 * @Created by Zhiend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    String username;
    String password;
    String phone;
}
