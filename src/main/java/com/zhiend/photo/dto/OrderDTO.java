package com.zhiend.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname OrderDTO
 * @Description TODO
 * @Date 2024/6/24 21:58
 * @Created by Zhiend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long userId;
    private Long photoId;
}
