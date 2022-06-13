package com.b635.notebook.Model.params;

import com.b635.notebook.Model.enums.NoteStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoteSearchParam extends BasePageParam {
    // 笔记标题
    private String title;

    // 分类id
    private Integer categoryId;

    // 笔记状态
    private NoteStatus status;
}
