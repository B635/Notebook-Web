package com.b635.notebook.Model.vo;

import com.b635.notebook.Model.enums.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class noteSimpleVo  implements Serializable {
    // 笔记编号
    private Integer id;

    // 笔记标题
    private String title;

    // 笔记分类
    private categoryVo category;

    // 笔记标签
    private List<tagVo> label;

    //笔记状态
    private NoteStatus status;

    // 笔记简介
    private String summary;

    // 保存时间
    private LocalDateTime date;

}
