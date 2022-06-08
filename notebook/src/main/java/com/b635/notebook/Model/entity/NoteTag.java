package com.b635.notebook.Model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(schema = "notebook")
public class NoteTag {
    // 笔记id
    @TableField("\"noteId\"")
    private Integer noteId;

    // 标签id
    @TableField("\"tagId\"")
    private Integer tagId;
}
