package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ExamResultInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface ExamResultInfoMapper extends BaseMapper<ExamResultInfo> {

    /**
     * 分页获取试卷结果信息
     *
     * @param page           分页对象
     * @param examResultInfo 试卷结果信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectExamResultPage(Page<ExamResultInfo> page, @Param("examResultInfo") ExamResultInfo examResultInfo);
}
