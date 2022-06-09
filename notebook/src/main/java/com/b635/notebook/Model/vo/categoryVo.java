package com.b635.notebook.Model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class categoryVo {

    // 分类编号
    private Integer id;

    // 分类名称
    private String name;

    // 分类描述
    private String description;

    // 笔记数量
    private Integer noteCount;
}
