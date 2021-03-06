package com.b635.notebook.Service;


import com.b635.notebook.Model.entity.Note;
import com.b635.notebook.Model.enums.NoteStatus;
import com.b635.notebook.Model.params.NoteSearchParam;
import com.b635.notebook.Model.vo.noteDetailVo;
import com.b635.notebook.Model.vo.noteSimpleVo;
import com.b635.notebook.utils.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface NoteService {
    // 获取所有笔记
    List<noteSimpleVo> listAllNote();

    // 根据分类id获取笔记数量, status为null是则获取全部数量
    int getCountByCategoryId(int categoryId, NoteStatus status);


    // 根据条件获取笔记分页信息
    IPage<Note> pageBy(NoteSearchParam param);

    // 根据笔记编号获取笔记信息
    noteDetailVo getDetailById(int noteId);

    // 根据笔记编号获取笔记部分信息
    noteSimpleVo getSimpleById(int noteId);

    // 更新笔记内容
    int update(noteDetailVo vo);

    // 删除笔记
    int deleteById(int noteId);

    // 添加笔记
    int add(noteDetailVo vo);

    // 根据ID查看笔记是否存在
    boolean isExist(int noteId);

    // Note to noteSimpleVo
    noteSimpleVo convertToSimpleVo(Note note);

    // Note to noteDetailVo
    noteDetailVo convertToDetailVo(Note note);

    // noteDetailVo to Note
    Note convertToNote(noteDetailVo noteDetailVo);

    // List<Note> to List<noteSimpleVo>
    List<noteSimpleVo> convertToListSimpleVo(List<Note> noteList);

    // IPage<Note> to PageResult<noteSimpleVo>
    PageResult<noteSimpleVo> covertToPageResult(IPage<Note> notePage);

}
