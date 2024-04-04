package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ExamOptionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface ExamOptionInfoMapper extends BaseMapper<ExamOptionInfo> {

    /**
     * 分页获取试卷选项信息
     *
     * @param page         分页对象
     * @param examOptionInfo 试卷选项信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectExamOption(Page<ExamOptionInfo> page, @Param("examOptionInfo") ExamOptionInfo examOptionInfo);
}
