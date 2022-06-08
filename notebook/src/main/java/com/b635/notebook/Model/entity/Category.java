package com.b635.notebook.Model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("notebook.category")
public class Category {
    // 分类id
    private Integer id;

    // 分类名
    private String name;

    // 分类描述
    private String description;
}
