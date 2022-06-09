package com.b635.notebook.Model.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class noteDetailVo extends noteSimpleVo {

    // 笔记内容
    private String content;

}
