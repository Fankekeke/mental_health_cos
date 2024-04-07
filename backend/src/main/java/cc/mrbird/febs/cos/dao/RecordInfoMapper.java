package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface RecordInfoMapper extends BaseMapper<RecordInfo> {

    /**
     * 分页获取考试记录信息
     *
     * @param page       分页对象
     * @param recordInfo 考试记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<RecordInfo> page, @Param("recordInfo") RecordInfo recordInfo);

    /**
     * 本月发帖
     *
     * @return 结果
     */
    BigDecimal selectIncomeMonth();

    /**
     * 本月工单
     *
     * @return 结果
     */
    Integer selectWorkOrderMonth();

    /**
     * 本年发帖
     *
     * @return 结果
     */
    BigDecimal selectIncomeYear();

    /**
     * 本年工单
     *
     * @return 结果
     */
    Integer selectWorkOrderYear();

    /**
     * 查询十天内发帖统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectPaymentRecord();

    /**
     * 查询十天内工单数量
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderRecord();
}
