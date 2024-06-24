package com.zhiend.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname UserLoginDTO
 * @Description TODO
 * @Date 2024/6/24 21:48
 * @Created by Zhiend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    String phone;
    String password;
}
