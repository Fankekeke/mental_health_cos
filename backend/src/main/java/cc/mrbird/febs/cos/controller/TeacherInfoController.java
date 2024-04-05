package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.TeacherInfo;
import cc.mrbird.febs.cos.service.ITeacherInfoService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/teacher-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherInfoController {

    private final ITeacherInfoService teacherInfoService;

    private final UserService userService;

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
     * @param userId 教师ID
     * @return 结果
     */
    @GetMapping("/detail/{userId}")
    public R teacherDetail(@PathVariable("userId") Integer userId) {
        return R.ok(teacherInfoService.getOne(Wrappers.<TeacherInfo>lambdaQuery().eq(TeacherInfo::getUserId, userId)));
    }

    /**
     * 教师信息列表
     *
     * @return 结果
     */
    @GetMapping("/list/check")
    public R listCheck() {
        return R.ok(teacherInfoService.list(Wrappers.<TeacherInfo>lambdaQuery().eq(TeacherInfo::getStatus, 1)));
    }

    /**
     * 新增教师信息
     *
     * @param teacherInfo 教师信息
     * @return 结果
     */
    @PostMapping
    public R save(TeacherInfo teacherInfo) throws Exception {
        teacherInfo.setCode("TEA-" + System.currentTimeMillis());
        teacherInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        userService.registTeacher(teacherInfo.getCode(), "1234qwer", teacherInfo);
        return R.ok(true);
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
