package com.b635.notebook.Model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(schema = "notebook")
public class Tag {
    // 标签id
    @TableId(value="id",type = IdType.AUTO )
    private Integer id;

    // 标签名
    private String name;
}
