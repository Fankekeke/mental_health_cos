package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ScheduleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface ScheduleInfoMapper extends BaseMapper<ScheduleInfo> {

    /**
     * 分页获取排班信息
     *
     * @param page         分页对象
     * @param scheduleInfo 排班信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySchedule(Page<ScheduleInfo> page, @Param("scheduleInfo") ScheduleInfo scheduleInfo);

    /**
     * 获取可预约的排班信息
     *
     * @return 列表
     */
    List<LinkedHashMap<String, Object>> queryScheduleReserve();
}
