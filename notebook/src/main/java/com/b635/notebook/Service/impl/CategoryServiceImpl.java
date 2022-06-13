package com.b635.notebook.Service.impl;

import com.b635.notebook.Mapper.CategoryMapper;
import com.b635.notebook.Mapper.NoteMapper;
import com.b635.notebook.Model.entity.Category;
import com.b635.notebook.Model.entity.Note;
import com.b635.notebook.Model.params.BasePageParam;
import com.b635.notebook.Model.vo.categoryVo;
import com.b635.notebook.Service.CategoryService;
import com.b635.notebook.utils.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private NoteMapper noteMapper;

    @Override
    public int getCount() {
        return categoryMapper.selectCount(null).intValue();
    }

    @Override
    public List<categoryVo> listUsedCategory() {
        List<Note> noteList = noteMapper.list();

        Set<Integer> categoryIds = noteList.stream()
                .map(Note::getCategory)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (categoryIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);

        return covertToListVo(categoryList);
    }

    @Override
    public List<categoryVo> listAllCategory() {
        List<Category> categoryList = categoryMapper.selectList(Wrappers.emptyWrapper());
        return covertToListVo(categoryList);
    }

    @Override
    public IPage<Category> pageBy(BasePageParam param) {
        Page<Category> page = new Page<>(param.getCurrent(), param.getPageSize());
        return categoryMapper.selectPage(page, Wrappers.emptyWrapper());
    }

    @Override
    public categoryVo getById(int categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return covertToVo(category);
    }

    @Override
    public categoryVo getByName(String name) {
        Category category = categoryMapper.selectOne(
                Wrappers.lambdaQuery(Category.class)
                        .eq(Category::getName, name)
        );
        return covertToVo(category);
    }

    @Override
    public int add(categoryVo categoryVo) {
        categoryVo byName = getByName(categoryVo.getName());
        if (Objects.nonNull(byName)) {
            return -byName.getId();
        }

        Category category = covertToCategory(categoryVo);
        categoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public int update(categoryVo categoryVo) {
        return categoryMapper.update(null,
                getNoteUpdateWrapper(categoryVo));
    }

    @Override
    public int deleteById(int categoryId) {
        int i = categoryMapper.deleteById(categoryId);
        if (i > 0) {
            noteMapper.update(null,
                    Wrappers.lambdaUpdate(Note.class)
                            .eq(Note::getCategory, categoryId)
                            .set(Note::getCategory, 0)
            );
        }
        return i;
    }

    @Override
    public categoryVo covertToVo(Category category) {
        if (category == null) {
            return null;
        }
        return new categoryVo(category.getId(),
                category.getName(), category.getDescription(),
                noteMapper.selectCount(Wrappers.lambdaQuery(Note.class)
                .eq(Note::getCategory, category.getId())).intValue());
    }

    @Override
    public Category covertToCategory(categoryVo categoryVo) {
        return new Category(categoryVo.getId(), categoryVo.getName(), categoryVo.getDescription());
    }

    @Override
    public List<categoryVo> covertToListVo(List<Category> categoryList) {
        return categoryList.stream().parallel()
                .map(this::covertToVo)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<categoryVo> convertToPageResult(IPage<Category> categoryIPage) {
        List<Category> categoryList = categoryIPage.getRecords();
        List<categoryVo> categoryVoList = covertToListVo(categoryList);
        return new PageResult<>(categoryIPage.getTotal(), categoryVoList);
    }

    private Wrapper<Category> getNoteUpdateWrapper(categoryVo categoryVo) {
        String name = categoryVo.getName();
        String description = categoryVo.getDescription();

        return Wrappers.lambdaUpdate(Category.class)
                .eq(Category::getId, categoryVo.getId())
                .set(StringUtils.hasText(name), Category::getName, name)
                .set(StringUtils.hasText(description), Category::getDescription, description);
    }
}
