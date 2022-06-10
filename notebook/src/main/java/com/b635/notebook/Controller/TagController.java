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
        int i = tagService.add(vo);
        if (i > 0) {
            return RUtils.result("标签成功添加");
        }
        if (i < 0) {
            return RUtils.result("标签名已存在");
        }
        return RUtils.result("标签添加失败");
    }
}
