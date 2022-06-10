package com.b635.notebook.Model.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = true)
public class noteDetailVo extends noteSimpleVo  implements Serializable {

    // 笔记内容
    private String content;

}
