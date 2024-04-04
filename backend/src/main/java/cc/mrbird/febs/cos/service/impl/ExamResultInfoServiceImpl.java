package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExamResultInfo;
import cc.mrbird.febs.cos.dao.ExamResultInfoMapper;
import cc.mrbird.febs.cos.service.IExamResultInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class ExamResultInfoServiceImpl extends ServiceImpl<ExamResultInfoMapper, ExamResultInfo> implements IExamResultInfoService {

    /**
     * 分页获取试卷结果信息
     *
     * @param page           分页对象
     * @param examResultInfo 试卷结果信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectExamResultPage(Page<ExamResultInfo> page, ExamResultInfo examResultInfo) {
        return baseMapper.selectExamResultPage(page, examResultInfo);
    }
}
