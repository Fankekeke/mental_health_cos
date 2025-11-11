package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ScheduleInfo;
import cc.mrbird.febs.cos.dao.ScheduleInfoMapper;
import cc.mrbird.febs.cos.entity.TeacherInfo;
import cc.mrbird.febs.cos.service.IScheduleInfoService;
import cc.mrbird.febs.cos.service.ITeacherInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleInfoServiceImpl extends ServiceImpl<ScheduleInfoMapper, ScheduleInfo> implements IScheduleInfoService {

    private final ITeacherInfoService teacherInfoService;

    /**
     * 分页获取排班信息
     *
     * @param page         分页对象
     * @param scheduleInfo 排班信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> querySchedule(Page<ScheduleInfo> page, ScheduleInfo scheduleInfo) {
        return baseMapper.querySchedule(page, scheduleInfo);
    }

    @Override
    public boolean batchSchedule() {
        List<TeacherInfo> teacherInfoList = teacherInfoService.list();
        if (CollectionUtil.isEmpty(teacherInfoList)) {
            return false;
        }

        // 当天是星期几
        int dayOfWeek = DateUtil.dayOfWeek(new Date());

        List<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        for (TeacherInfo teacherInfo : teacherInfoList) {
            if (StrUtil.isEmpty(teacherInfo.getWeeks()) || StrUtil.isEmpty(teacherInfo.getWorkTimes())) {
                continue;
            }
            List<String> weekList = StrUtil.split(teacherInfo.getWeeks(), ',');
            if (!weekList.contains(StrUtil.toString(dayOfWeek))) {
                continue;
            }
            List<String> workTimesList = StrUtil.split(teacherInfo.getWorkTimes(), ',');
            for (String workTime : workTimesList) {
                String startTime = workTime.split("-")[0];
                String endTime = workTime.split("-")[1];
                ScheduleInfo scheduleInfo = new ScheduleInfo();
                scheduleInfo.setName(teacherInfo.getName() + " 排班 " + DateUtil.format(new Date(), "yyyy-MM-dd") + startTime + " - " + endTime);
                scheduleInfo.setStartDate(startTime);
                scheduleInfo.setEndDate(endTime);
                scheduleInfo.setStaffIds(teacherInfo.getId().toString());
                scheduleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
                scheduleInfo.setTaskDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
                scheduleInfo.setStatus("1");
                scheduleInfoList.add(scheduleInfo);
            }
        }
        if (CollectionUtil.isNotEmpty(scheduleInfoList)) {
            return this.saveBatch(scheduleInfoList);
        }
        return true;
    }

    /**
     * 获取可预约的排班信息
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryScheduleReserve() {
        return baseMapper.queryScheduleReserve();
    }
}
