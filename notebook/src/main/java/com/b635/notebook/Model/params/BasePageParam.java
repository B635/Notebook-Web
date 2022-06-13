package com.b635.notebook.Model.params;


import lombok.Data;

@Data
public class BasePageParam {
    // 当前页
    private long current;

    // 每页数量
    private long pageSize;
}
