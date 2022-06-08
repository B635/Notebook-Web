package com.b635.notebook.Model.vo;

import com.b635.notebook.Model.enums.NoteStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@TableName("notebook.noteSimple")
public class noteSimpleVo {
    // 笔记编号
    private Integer id;

    // 笔记标题
    private String title;

    // 笔记分类
    private String category;

    // 笔记标签
    private String label;

    //笔记状态
    private NoteStatus state;

    // 保存时间
    private OffsetDateTime date;
}
