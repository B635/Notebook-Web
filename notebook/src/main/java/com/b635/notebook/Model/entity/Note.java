package com.b635.notebook.Model.entity;

import com.b635.notebook.Model.enums.NoteStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@TableName(schema = "notebook")
public class Note {
    // 笔记编号
    @TableId(value="id",type = IdType.AUTO )
    private Integer id;

    // 笔记标题
    private String title;

    // 笔记内容
    private String content;

    // 笔记分类
    private Integer category;

    // 笔记简介
    private String summary;

    //笔记状态
    private NoteStatus status;

    // 保存时间
    private OffsetDateTime date;

}
