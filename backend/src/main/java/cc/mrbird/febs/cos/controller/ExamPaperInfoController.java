package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.FileDownloadUtils;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ExamPaperInfo;
import cc.mrbird.febs.cos.service.IExamPaperInfoService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
        return R.ok(examPaperInfoService.selectExamPaperPage(page, examPaperInfo));
    }

    @GetMapping("/audit")
    public R audit(Integer id, String status) {
        return R.ok(examPaperInfoService.update(Wrappers.<ExamPaperInfo>lambdaUpdate().set(ExamPaperInfo::getStatus, status).eq(ExamPaperInfo::getId, id)));
    }

    /**
     * 下载模板
     */
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            FileDownloadUtils.downloadTemplate(response, "试卷模板.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入专家信息列表
     */
    @PostMapping("/import")
    public R importExcel(@RequestParam("file") MultipartFile file) {
        try {
            String errorMsg = examPaperInfoService.importExcel(file);
            if (StrUtil.isNotEmpty(errorMsg)) {
                return R.error(errorMsg);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("导入异常");
    }

    /**
     * 试卷信息详情
     *
     * @param id 试卷选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(examPaperInfoService.detail(id));
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
