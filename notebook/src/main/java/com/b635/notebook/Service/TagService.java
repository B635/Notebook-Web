package com.b635.notebook.Service;

import com.b635.notebook.Model.entity.Tag;
import com.b635.notebook.Model.vo.tagVo;

import java.util.List;

public interface TagService {
    // 获取所有笔记标签的数量
    int getCount();

    // 获取笔记的标签
    List<tagVo> listNoteUsed(int noteId);

    // 获取所有标签
    List<tagVo> listAll();

    // 获取已使用的标签
    List<tagVo> listUsed();

    // 根据名称获取单个标签
    tagVo getByName(String name);

    // 添加标签
    int add(tagVo tagVo);

    // 添加博客标签表
    int addNoteTag(int noteId, int tagId);

    // 更新标签
    int update(tagVo tagVo);

    // 更新笔记标签
    int updateNoteUsed(int noteId, List<tagVo> tagVoList);

    // 删除标签
    int deleteById(int tagId);

    // 删除笔记标签
    int deleteNoteUsed(int noteId);

    // 删除笔记标签表
    int deleteNoteTag(int noteId, int tagId);

    // Tag to tagVo
    tagVo convertToTagVo(Tag tag);

    // tagVo to Tag
    Tag convertToTag(tagVo tagVo);

    // List<Tag> to List<tagVo>
    List<tagVo> convertToTagVoList(List<Tag> tagList);
}
