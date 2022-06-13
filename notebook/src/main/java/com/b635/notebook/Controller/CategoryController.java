package com.b635.notebook.Controller;

import com.b635.notebook.Model.vo.categoryVo;
import com.b635.notebook.Service.CategoryService;
import com.b635.notebook.Service.NoteService;
import com.b635.notebook.utils.R;
import com.b635.notebook.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NoteService noteService;

    @ResponseBody
    @GetMapping("/list/all")
    public R listAll() {
        List<categoryVo> categoryVoList = categoryService.listAllCategory();
        return RUtils.result("所有笔记分类", categoryVoList);
    }

    @ResponseBody
    @GetMapping("/list/used")
    public R listUsed() {
        List<categoryVo> usedCategory = categoryService.listUsedCategory();
        return RUtils.result("已使用的笔记分类", usedCategory);
    }

    @ResponseBody
    @DeleteMapping("/delete/{categoryId}")
    public R delete(@PathVariable("categoryId") Integer categoryId) {
        int result = categoryService.deleteById(categoryId);
        return RUtils.commonFailOrNot(result, "删除分类");
    }

    @ResponseBody
    @PostMapping("/add")
    public R add(@RequestBody categoryVo vo) {
        int result = categoryService.add(vo);
        return RUtils.commonFailOrNot(result, "添加分类");
    }

    @ResponseBody
    @PostMapping("/update")
    public R update(@RequestBody categoryVo vo) {
        int result = categoryService.update(vo);
        return RUtils.commonFailOrNot(result, "更新分类");
    }
}
