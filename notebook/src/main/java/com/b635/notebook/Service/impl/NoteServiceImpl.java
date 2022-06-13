package com.b635.notebook.Service.impl;

import com.b635.notebook.Mapper.NoteMapper;
import com.b635.notebook.Model.entity.Note;
import com.b635.notebook.Model.enums.NoteStatus;
import com.b635.notebook.Model.params.NoteSearchParam;
import com.b635.notebook.Model.vo.categoryVo;
import com.b635.notebook.Model.vo.noteDetailVo;
import com.b635.notebook.Model.vo.noteSimpleVo;
import com.b635.notebook.Model.vo.tagVo;
import com.b635.notebook.Service.CategoryService;
import com.b635.notebook.Service.NoteService;
import com.b635.notebook.Service.TagService;
import com.b635.notebook.utils.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    @Resource
    private NoteMapper noteMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<noteSimpleVo> listAllNote() {
        List<Note> noteList = noteMapper.selectList(Wrappers.emptyWrapper());
        return convertToListSimpleVo(noteList);
    }

    @Override
    public int getCountByCategoryId(int categoryId, NoteStatus status) {
        return noteMapper.selectCount(Wrappers.lambdaQuery(Note.class)
                .eq(Note::getCategory, categoryId)
                .eq(Objects.nonNull(status), Note::getStatus, status)
        ).intValue();
    }

    @Override
    public IPage<Note> pageBy(NoteSearchParam param) {
        Page<Note> page = new Page<>(param.getCurrent(), param.getPageSize());
        Wrapper<Note> wrapper = getSearchWrapper(param);
        return noteMapper.selectPage(page, wrapper);
    }

    @Override
    public noteDetailVo getDetailById(int noteId) {
        Note note = noteMapper.selectById(noteId);
        return convertToDetailVo(note);
    }

    @Override
    public noteSimpleVo getSimpleById(int noteId) {
        Note note = noteMapper.selectById(noteId);
        return convertToSimpleVo(note);
    }

    @Override
    public int update(noteDetailVo vo) {
        if (Objects.nonNull(vo.getLabel())) {
            tagService.updateNoteUsed(vo.getId(), vo.getLabel());
        }
        return noteMapper.update(null, getUpdateWrapper(vo));

    }

    @Override
    public int deleteById(int noteId) {
        return noteMapper.deleteById(noteId);
    }

    @Override
    public int add(noteDetailVo vo) {
        Note note = convertToNote(vo);
        if (!StringUtils.hasText(note.getTitle())) {
            note.setTitle(OffsetDateTime.now().toString());
        }
        if (!StringUtils.hasText(note.getSummary())) {
            note.setSummary("");
        }
        if (Objects.isNull(note.getStatus())) {
            note.setStatus(NoteStatus.SAVED);
        }
        if (Objects.isNull(note.getDate())) {
            note.setDate(OffsetDateTime.now());
        }

        categoryVo category = vo.getCategory();
        Integer cid = Optional.ofNullable(category)
                .map(categoryVo::getId)
                .filter(e -> e > 0)
                .orElse(0);
        note.setCategory(cid);

        noteMapper.insert(note);

        Integer noteId = note.getId();

        tagService.updateNoteUsed(noteId, vo.getLabel());

        return noteId;
    }

    @Override
    public boolean isExist(int noteId) {
        Note note = noteMapper.selectOne(
                Wrappers.lambdaQuery(Note.class)
                        .select(Note::getId)
                        .eq(Note::getId, noteId)
        );
        return Objects.nonNull(note);
    }

    @Override
    public noteSimpleVo convertToSimpleVo(Note note) {
        noteSimpleVo noteSimpleVo = new noteSimpleVo();

        Integer categoryId = note.getCategory();
        categoryVo categoryVO = categoryService.getById(Objects.isNull(categoryId) ? 0 : categoryId);
        noteSimpleVo.setCategory(categoryVO);

        List<tagVo> noteTagList = tagService.listNoteUsed(note.getId());
        noteSimpleVo.setLabel(noteTagList);

        noteSimpleVo.setId(note.getId());
        noteSimpleVo.setTitle(note.getTitle());
        noteSimpleVo.setStatus(note.getStatus());
        noteSimpleVo.setSummary(note.getSummary());
        noteSimpleVo.setDate(note.getDate());

        return noteSimpleVo;
    }

    @Override
    public noteDetailVo convertToDetailVo(Note note) {
        if (Objects.isNull(note)) {
            return null;
        }
        noteDetailVo noteDetailVo = new noteDetailVo();
        noteDetailVo.setId(note.getId());
        noteDetailVo.setTitle(note.getTitle());
        noteDetailVo.setStatus(note.getStatus());
        noteDetailVo.setSummary(note.getSummary());
        noteDetailVo.setDate(note.getDate());


        Integer categoryId = note.getCategory();
        categoryVo categoryVo = categoryService.getById(Objects.isNull(categoryId) ? 0 : categoryId);
        noteDetailVo.setCategory(categoryVo);

        List<tagVo> noteTagList = tagService.listNoteUsed(note.getId());
        noteDetailVo.setLabel(noteTagList);

        return noteDetailVo;
    }

    @Override
    public Note convertToNote(noteDetailVo noteDetailVo) {
        Integer categoryId = noteDetailVo.getCategory().getId();

        return new Note(noteDetailVo.getId(), noteDetailVo.getTitle(),
                noteDetailVo.getContent(), categoryId, noteDetailVo.getSummary(),
                noteDetailVo.getStatus(), noteDetailVo.getDate());
    }

    @Override
    public List<noteSimpleVo> convertToListSimpleVo(List<Note> noteList) {
        return noteList.stream().parallel()
                .map(this::convertToSimpleVo)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<noteSimpleVo> covertToPageResult(IPage<Note> notePage) {
        List<noteSimpleVo> noteSimpleVoList = convertToListSimpleVo(notePage.getRecords());
        return new PageResult<>(notePage.getTotal(), noteSimpleVoList);
    }

    // 获取搜索条件
    private Wrapper<Note> getSearchWrapper(NoteSearchParam param) {
        NoteStatus status = param.getStatus();
        Integer categoryId = param.getCategoryId();
        String title = param.getTitle();


        return Wrappers.lambdaQuery(Note.class)
                .eq(Objects.nonNull(status), Note::getStatus, status)
                .eq(Objects.nonNull(categoryId), Note::getCategory, categoryId)
                .like(!StringUtils.isEmpty(title), Note::getTitle, title)
                .orderByDesc(Note::getId);
    }

    // 获取博客更新条件
    private Wrapper<Note> getUpdateWrapper(noteDetailVo bd) {
        Integer id = bd.getId();
        String title = bd.getTitle();
        String summary = bd.getSummary();
        NoteStatus status = bd.getStatus();
        categoryVo category = bd.getCategory();
        String content = bd.getContent();

        boolean c = Objects.nonNull(category)
                && Objects.nonNull(category.getId())
                && category.getId() > 0;

        return Wrappers.lambdaUpdate(Note.class)
                .eq(Note::getId, id)
                .set(Objects.nonNull(status), Note::getStatus, status)
                .set(c, Note::getCategory, c? category.getId() : 0)
                .set(StringUtils.hasText(title), Note::getTitle, title)
                .set(!StringUtils.isEmpty(summary), Note::getSummary, summary)
                .set(!StringUtils.isEmpty(content), Note::getContent, content);
    }
}
