package com.b635.notebook.utils;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {
    // 数据总数
    private long total;

    // 分页数据
    private List<T> list;

    public PageResult(List<T> list) {
        this.total = list.size();
        this.list = list;
    }

    public PageResult(long total, @NonNull List<T> list) {
        this.total = total;
        this.list = list;
    }
}
