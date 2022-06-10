package com.b635.notebook.utils;

import lombok.Data;
import java.io.Serializable;

@Data
public class R implements Serializable {

    // 响应消息
    private String message;

    // 响应数据
    private Object data;

    public R(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
