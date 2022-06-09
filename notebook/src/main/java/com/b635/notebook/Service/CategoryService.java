package com.b635.notebook.Service;

import com.b635.notebook.Model.entity.Category;
import com.b635.notebook.Model.vo.categoryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CategoryService {
    // 获取分类数量
    int getCount();

    // 获取已使用的博客分类
    List<categoryVo> listUsedCategory();

    // 获取所有分类
    List<categoryVo> listAllCategory();

    // 根据分类id获取分类信息
    categoryVo getById(int categoryId);

    // 根据分类名回去分类信息
    categoryVo getByName(String name);

    // 添加分类
    int add(categoryVo categoryVo);

    // 更新分类信息
    int update(categoryVo categoryVo);

    // 删除分类
    int deleteById(int categoryId);

    // Category to categoryVo
    categoryVo covertToVo(Category category);

    // categoryVo to Category
    Category covertToCategory(categoryVo categoryVo);


    // List<Category> to List<categoryVo>
    List<categoryVo> covertToListVo(List<Category> categoryList);

}
