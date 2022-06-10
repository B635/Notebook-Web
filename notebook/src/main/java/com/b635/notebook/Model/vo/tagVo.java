package com.b635.notebook.Model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class tagVo implements Serializable {
    // 标签编号
    private Integer id;

    // 标签名称
    private String name;
}
