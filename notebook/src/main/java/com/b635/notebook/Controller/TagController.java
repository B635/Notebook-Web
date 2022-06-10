package com.b635.notebook.Controller;


import com.b635.notebook.Model.vo.tagVo;
import com.b635.notebook.Service.TagService;
import com.b635.notebook.utils.R;
import com.b635.notebook.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ResponseBody
    @GetMapping("/list")
    public R listAllTags() {
        return RUtils.result("所有标签", tagService.listAll());
    }

    @ResponseBody
    @PostMapping("/add")
    public R add(@RequestBody tagVo vo) {
        int result = tagService.add(vo);
        if (result > 0) {
            return RUtils.result("标签成功添加");
        }
        if (result < 0) {
            return RUtils.result("标签名已存在");
        }
        return RUtils.result("标签添加失败");
    }

    @ResponseBody
    @PostMapping("/update")
    public R update(@RequestBody tagVo tagVo) {
        int result = tagService.update(tagVo);
        return RUtils.commonFailOrNot(result, "标签更新");
    }

    @ResponseBody
    @DeleteMapping("/delete/{tagId}")
    public R delete(@PathVariable("tagId") Integer tagId) {
        int result = tagService.deleteById(tagId);
        return RUtils.commonFailOrNot(result, "标签删除");
    }
}
