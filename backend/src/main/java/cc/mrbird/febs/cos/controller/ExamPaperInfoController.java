package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ExamPaperInfo;
import cc.mrbird.febs.cos.service.IExamPaperInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/exam-paper-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamPaperInfoController {

    private final IExamPaperInfoService examPaperInfoService;

    /**
     * 分页获取试卷信息
     *
     * @param page          分页对象
     * @param examPaperInfo 试卷信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ExamPaperInfo> page, ExamPaperInfo examPaperInfo) {
        return R.ok();
    }

    /**
     * 试卷信息详情
     *
     * @param id 试卷选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(examPaperInfoService.getById(id));
    }

    /**
     * 试卷信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(examPaperInfoService.list());
    }

    /**
     * 新增试卷信息
     *
     * @param examPaperInfo 试卷信息
     * @return 结果
     */
    @PostMapping
    public R save(ExamPaperInfo examPaperInfo) {
        return R.ok(examPaperInfoService.save(examPaperInfo));
    }

    /**
     * 修改试卷信息
     *
     * @param examPaperInfo 试卷信息
     * @return 结果
     */
    @PutMapping
    public R edit(ExamPaperInfo examPaperInfo) {
        return R.ok(examPaperInfoService.updateById(examPaperInfo));
    }

    /**
     * 删除试卷信息
     *
     * @param ids ids
     * @return 试卷信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(examPaperInfoService.removeByIds(ids));
    }
}
