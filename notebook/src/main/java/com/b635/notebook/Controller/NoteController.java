package com.b635.notebook.Controller;

import com.b635.notebook.Model.vo.noteDetailVo;
import com.b635.notebook.Service.NoteService;
import com.b635.notebook.utils.R;
import com.b635.notebook.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @ResponseBody
    @GetMapping("/list")
    public R listAllNote() {
        return RUtils.result("所有笔记", noteService.listAllNote());
    }

    @ResponseBody
    @GetMapping("/get/{id}")
    public R getNote(@PathVariable("id") Integer noteId) {
        noteDetailVo detailVo = noteService.getDetailById(noteId);
        if (Objects.isNull(detailVo)) {
            return RUtils.result("无此笔记信息");
        }
        return RUtils.result("笔记信息", detailVo);
    }

    @ResponseBody
    @PostMapping("/add")
    public R addNote(@RequestBody noteDetailVo vo) {
        int result = noteService.add(vo);
        return RUtils.commonFailOrNot(result, "发表博客文章");
    }

    @ResponseBody
    @PostMapping("update")
    public R updateNote(@RequestBody noteDetailVo vo) {
        int result = noteService.update(vo);
        return RUtils.commonFailOrNot(result, "笔记更新");
    }

    @ResponseBody
    @DeleteMapping("/delete/{noteId}")
    public R deleteNote(@PathVariable("noteId") Integer noteId) {
        int result = noteService.deleteById(noteId);
        return RUtils.commonFailOrNot(result, "删除笔记");
    }

}
