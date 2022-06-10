package com.b635.notebook.Service.impl;


import com.b635.notebook.Mapper.NoteTagMapper;
import com.b635.notebook.Mapper.TagMapper;
import com.b635.notebook.Model.entity.NoteTag;
import com.b635.notebook.Model.entity.Tag;
import com.b635.notebook.Model.vo.tagVo;
import com.b635.notebook.Service.TagService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private NoteTagMapper noteTagMapper;

    @Override
    public int getCount() {
        return  tagMapper.selectCount(null).intValue();
    }

    @Override
    public List<tagVo> listNoteUsed(int noteId) {
        List<NoteTag> noteTagList = noteTagMapper.selectList(
                Wrappers.lambdaQuery(NoteTag.class).eq(NoteTag::getNoteId, noteId)
        );

        if (noteTagList.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Integer> tagIdList = noteTagList.stream()
                .map(NoteTag::getTagId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Tag> tagList = tagMapper.selectBatchIds(tagIdList);

        return convertToTagVoList(tagList);
    }

    @Override
    public List<tagVo> listAll() {
        List<Tag> tagList = tagMapper.selectList(Wrappers.emptyWrapper());
        return convertToTagVoList(tagList);
    }

    @Override
    public List<tagVo> listUsed() {
        List<NoteTag> noteTagList = noteTagMapper.selectList(
                Wrappers.lambdaQuery(NoteTag.class).select(NoteTag::getTagId)
        );
        if (CollectionUtils.isEmpty(noteTagList)) {
            return Collections.emptyList();
        }

        Set<Integer> tagIdSet = noteTagList.stream()
                .map(NoteTag::getTagId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<Tag> tagList = tagMapper.selectBatchIds(tagIdSet);
        return convertToTagVoList(tagList);
    }

    @Override
    public tagVo getByName(String name) {
        Tag tag = tagMapper.selectOne(
                Wrappers.lambdaQuery(Tag.class)
                        .eq(Tag::getName, name)
        );
        return convertToTagVo(tag);
    }


    @Override
    public int add(tagVo tagVo) {
        tagVo byName = getByName(tagVo.getName());

        if (Objects.nonNull(byName)) {
            return -byName.getId();
        }
        Tag tag = convertToTag(tagVo);
        tagMapper.insert(tag);

        return tag.getId();
    }

    @Override
    public int addNoteTag(int noteId, int tagId) {
        NoteTag noteTag = new NoteTag(noteId, tagId);
        noteTagMapper.insert(noteTag);
        return noteTag.getNoteId();
    }

    @Override
    public int update(tagVo tagVo) {
        return tagMapper.update(null,
                Wrappers.lambdaUpdate(Tag.class)
                        .eq(Tag::getId, tagVo.getId())
                        .set(Tag::getName, tagVo.getName())
        );
    }

    @Override
    public int updateNoteUsed(int noteId, List<tagVo> tagVoList) {
        if (tagVoList.isEmpty()) {
            return deleteNoteUsed(noteId);
        }

        Set<Integer> oldTagIdSet = listNoteUsed(noteId).stream()
                .map(tagVo::getId)
                .collect(Collectors.toSet());

        Set<Integer> newTagIdSet = tagVoList.stream()
                .map(tagVo::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (tagVoList.size() == oldTagIdSet.size() &&
        newTagIdSet.containsAll(oldTagIdSet)) {
            return 0;
        }

        Set<Integer> expect = oldTagIdSet.stream()
                .filter(newTagIdSet::contains)
                .collect(Collectors.toSet());

        int count = 0;
        for (tagVo vo : tagVoList) {
            if (Objects.isNull(vo.getId()) && StringUtils.hasText(vo.getName())) {
                int tagId = add(vo);

                if (tagId < 0 && !oldTagIdSet.contains(-tagId) || tagId > 0) {
                    addNoteTag(noteId, Math.abs(tagId));
                    count++;
                }
            } else {
                if (!expect.contains(vo.getId())) {
                    addNoteTag(noteId, vo.getId());
                    count++;
                }
            }
        }

        oldTagIdSet.removeAll(expect);
        for (Integer tagId : oldTagIdSet) {
            deleteNoteTag(noteId, tagId);
            count++;
        }

        return count;
    }

    @Override
    public int deleteById(int tagId) {
        int i = tagMapper.deleteById(tagId);

        if (i > 0) {
            noteTagMapper.delete(
                    Wrappers.lambdaQuery(NoteTag.class)
                            .eq(NoteTag::getTagId, tagId)
            );
        }
        return i;
    }

    @Override
    public int deleteNoteUsed(int noteId) {
        return noteTagMapper.delete(
                Wrappers.lambdaQuery(NoteTag.class)
                        .eq(NoteTag::getNoteId, noteId)
        );
    }

    @Override
    public int deleteNoteTag(int noteId, int tagId) {
        return noteTagMapper.delete(
                Wrappers.lambdaQuery(NoteTag.class)
                        .eq(NoteTag::getNoteId, noteId)
                        .eq(NoteTag::getTagId, tagId)
        );
    }

    @Override
    public tagVo convertToTagVo(Tag tag) {
        if (tag == null) {
            return null;
        }
        return new tagVo(tag.getId(), tag.getName());
    }

    @Override
    public Tag convertToTag(tagVo tagVo) {
        return new Tag(tagVo.getId(), tagVo.getName());
    }

    @Override
    public List<tagVo> convertToTagVoList(List<Tag> tagList) {
        return tagList.stream().parallel()
                .map(this::convertToTagVo)
                .collect(Collectors.toList());
    }
}
