package com.b635.notebook.Mapper;

import com.b635.notebook.Model.entity.Note;
import com.b635.notebook.Model.params.NoteSearchParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteMapper extends BaseMapper<Note> {
    List<Note> list();

}
