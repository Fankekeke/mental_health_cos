package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RegisterInfo;
import cc.mrbird.febs.cos.entity.ScheduleInfo;
import cc.mrbird.febs.cos.entity.StudentInfo;
import cc.mrbird.febs.cos.service.IRegisterInfoService;
import cc.mrbird.febs.cos.service.IScheduleInfoService;
import cc.mrbird.febs.cos.service.IStudentInfoService;
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
@RequestMapping("/cos/register-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterInfoController {

    private final IRegisterInfoService registerInfoService;

    private final IStudentInfoService studentInfoService;

    private final IScheduleInfoService scheduleInfoService;

    /**
     * 分页获取辅导预约信息
     *
     * @param page         分页对象
     * @param registerInfo 辅导预约信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RegisterInfo> page, RegisterInfo registerInfo) {
        return R.ok(registerInfoService.queryRegisterPage(page, registerInfo));
    }

    /**
     * 根据用户ID查询预约信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/byUser/{userId}")
    public R byUser(@PathVariable("userId") Integer userId) {
        return R.ok(registerInfoService.list(Wrappers.<RegisterInfo>lambdaQuery().eq(RegisterInfo::getUserId, userId)));
    }

    /**
     * 辅导预约信息详情
     *
     * @param id 辅导预约选项ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(registerInfoService.getById(id));
    }

    /**
     * 辅导预约信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(registerInfoService.list());
    }

    /**
     * 新增辅导预约信息
     *
     * @param registerInfo 辅导预约信息
     * @return 结果
     */
    @PostMapping
    public R save(RegisterInfo registerInfo) {
        StudentInfo studentInfo = studentInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, registerInfo.getUserId()));
        registerInfo.setUserId(studentInfo.getId());
        registerInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        scheduleInfoService.update(Wrappers.<ScheduleInfo>lambdaUpdate().set(ScheduleInfo::getCheckFlag, "1").eq(ScheduleInfo::getId, registerInfo.getScheduleId()));
        return R.ok(registerInfoService.save(registerInfo));
    }

    /**
     * 修改辅导预约信息
     *
     * @param registerInfo 辅导预约信息
     * @return 结果
     */
    @PutMapping
    public R edit(RegisterInfo registerInfo) {
        registerInfo.setStatus("2");
        return R.ok(registerInfoService.updateById(registerInfo));
    }

    /**
     * 删除辅导预约信息
     *
     * @param ids ids
     * @return 辅导预约信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(registerInfoService.removeByIds(ids));
    }
}
