package com.b635.notebook.Model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@TableName("notebook.note")
public class Note {
    // 笔记编号
    private Integer id;

    // 笔记标题
    private String title;

    // 笔记内容
    private String content;

    // 笔记分类
    private String category;

    // 笔记标签
    private String label;

    // 笔记简介
    private String summary;

    //笔记状态
    private Integer state;

    // 保存时间
    private OffsetDateTime date;
}
