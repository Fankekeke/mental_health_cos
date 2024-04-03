package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RecordInfo;
import cc.mrbird.febs.cos.service.IRecordInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/record-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordInfoController {

    private final IRecordInfoService recordInfoService;

    /**
     * 分页获取考试记录信息
     *
     * @param page       分页对象
     * @param recordInfo 考试记录信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RecordInfo> page, RecordInfo recordInfo) {
        return R.ok();
    }

    /**
     * 考试记录信息详情
     *
     * @param id 考试记录选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(recordInfoService.getById(id));
    }

    /**
     * 考试记录信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(recordInfoService.list());
    }

    /**
     * 新增考试记录信息
     *
     * @param recordInfo 考试记录信息
     * @return 结果
     */
    @PostMapping
    public R save(RecordInfo recordInfo) {
        return R.ok(recordInfoService.save(recordInfo));
    }

    /**
     * 修改考试记录信息
     *
     * @param recordInfo 考试记录信息
     * @return 结果
     */
    @PutMapping
    public R edit(RecordInfo recordInfo) {
        return R.ok(recordInfoService.updateById(recordInfo));
    }

    /**
     * 删除考试记录信息
     *
     * @param ids ids
     * @return 考试记录信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(recordInfoService.removeByIds(ids));
    }
}
