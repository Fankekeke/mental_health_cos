package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ScheduleInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IScheduleInfoService extends IService<ScheduleInfo> {

    /**
     * 分页获取排班信息
     *
     * @param page         分页对象
     * @param scheduleInfo 排班信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySchedule(Page<ScheduleInfo> page, ScheduleInfo scheduleInfo);

    /**
     * 批量生成排班信息
     *
     * @return 结果
     */
    boolean batchSchedule();

    /**
     * 获取可预约的排班信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryScheduleReserve();
}
