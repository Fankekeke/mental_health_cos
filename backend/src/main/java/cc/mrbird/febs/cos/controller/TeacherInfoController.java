package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.TeacherInfo;
import cc.mrbird.febs.cos.service.ITeacherInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/teacher-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherInfoController {

    private final ITeacherInfoService teacherInfoService;

    /**
     * 分页获取教师信息
     *
     * @param page        分页对象
     * @param teacherInfo 教师信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<TeacherInfo> page, TeacherInfo teacherInfo) {
        return R.ok(teacherInfoService.selectTeacherPage(page, teacherInfo));
    }

    /**
     * 教师信息详情
     *
     * @param id 教师选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(teacherInfoService.getById(id));
    }

    /**
     * 教师信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(teacherInfoService.list());
    }

    /**
     * 新增教师信息
     *
     * @param teacherInfo 教师信息
     * @return 结果
     */
    @PostMapping
    public R save(TeacherInfo teacherInfo) {
        return R.ok(teacherInfoService.save(teacherInfo));
    }

    /**
     * 修改教师信息
     *
     * @param teacherInfo 教师信息
     * @return 结果
     */
    @PutMapping
    public R edit(TeacherInfo teacherInfo) {
        return R.ok(teacherInfoService.updateById(teacherInfo));
    }

    /**
     * 删除教师信息
     *
     * @param ids ids
     * @return 教师信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(teacherInfoService.removeByIds(ids));
    }
}
