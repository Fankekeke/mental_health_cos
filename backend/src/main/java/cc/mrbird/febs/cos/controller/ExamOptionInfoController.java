package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ExamOptionInfo;
import cc.mrbird.febs.cos.service.IExamOptionInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/exam-option-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamOptionInfoController {

    private final IExamOptionInfoService examOptionInfoService;

    /**
     * 分页获取试卷选项信息
     *
     * @param page         分页对象
     * @param examOptionInfo 试卷选项信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ExamOptionInfo> page, ExamOptionInfo examOptionInfo) {
        return R.ok();
    }

    /**
     * 试卷选项信息详情
     *
     * @param id 试卷选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(examOptionInfoService.getById(id));
    }

    /**
     * 试卷选项信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(examOptionInfoService.list());
    }

    /**
     * 新增试卷选项信息
     *
     * @param examOptionInfo 试卷选项信息
     * @return 结果
     */
    @PostMapping
    public R save(ExamOptionInfo examOptionInfo) {
        return R.ok(examOptionInfoService.save(examOptionInfo));
    }

    /**
     * 修改试卷选项信息
     *
     * @param examOptionInfo 试卷选项信息
     * @return 结果
     */
    @PutMapping
    public R edit(ExamOptionInfo examOptionInfo) {
        return R.ok(examOptionInfoService.updateById(examOptionInfo));
    }

    /**
     * 删除试卷选项信息
     *
     * @param ids ids
     * @return 试卷选项信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(examOptionInfoService.removeByIds(ids));
    }
}
