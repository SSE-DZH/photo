package com.zhiend.photo.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum PaymentStatus {
    PAID("paid"),
    UNPAID("unpaid");

    @EnumValue
    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
