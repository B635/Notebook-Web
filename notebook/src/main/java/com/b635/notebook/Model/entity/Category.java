package com.b635.notebook.Model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(schema = "notebook")
public class Category {
    // 分类id
    @TableId(value="id",type = IdType.AUTO )
    private Integer id;

    // 分类名
    private String name;

    // 分类描述
    private String description;
}
