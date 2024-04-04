package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ExamResultInfo;
import cc.mrbird.febs.cos.service.IExamResultInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/exam-result-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamResultInfoController {

    private final IExamResultInfoService examResultInfoService;

    /**
     * 分页获取试卷结果信息
     *
     * @param page           分页对象
     * @param examResultInfo 试卷结果信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ExamResultInfo> page, ExamResultInfo examResultInfo) {
        return R.ok(examResultInfoService.selectExamResultPage(page, examResultInfo));
    }

    /**
     * 试卷结果信息详情
     *
     * @param id 试卷结果选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(examResultInfoService.getById(id));
    }

    /**
     * 试卷结果信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(examResultInfoService.list());
    }

    /**
     * 新增试卷结果信息
     *
     * @param examResultInfo 试卷结果信息
     * @return 结果
     */
    @PostMapping
    public R save(ExamResultInfo examResultInfo) {
        return R.ok(examResultInfoService.save(examResultInfo));
    }

    /**
     * 修改试卷结果信息
     *
     * @param examResultInfo 试卷结果信息
     * @return 结果
     */
    @PutMapping
    public R edit(ExamResultInfo examResultInfo) {
        return R.ok(examResultInfoService.updateById(examResultInfo));
    }

    /**
     * 删除试卷结果信息
     *
     * @param ids ids
     * @return 试卷结果信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(examResultInfoService.removeByIds(ids));
    }
}
