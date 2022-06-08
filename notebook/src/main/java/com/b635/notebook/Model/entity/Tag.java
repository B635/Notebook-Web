package com.b635.notebook.Model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("notebook.tag")
public class Tag {
    // 标签id
    private Integer id;

    // 标签名
    private String name;
}
