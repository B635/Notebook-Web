package com.b635.notebook.Model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NoteStatus implements IEnum<Integer> {

    // 已保存
    SAVED(0),

    // 草稿
    DRAFT(1),

    // 回收站
    RECYCLE(2);

    private final int value;

    NoteStatus(int value) { this.value = value; }

    @Override
    public Integer getValue() {
        return value;
    }
}
