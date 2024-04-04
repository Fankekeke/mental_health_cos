package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExamOptionInfo;
import cc.mrbird.febs.cos.dao.ExamOptionInfoMapper;
import cc.mrbird.febs.cos.service.IExamOptionInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class ExamOptionInfoServiceImpl extends ServiceImpl<ExamOptionInfoMapper, ExamOptionInfo> implements IExamOptionInfoService {

    /**
     * 分页获取试卷选项信息
     *
     * @param page         分页对象
     * @param examOptionInfo 试卷选项信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectExamOption(Page<ExamOptionInfo> page, ExamOptionInfo examOptionInfo) {
        return baseMapper.selectExamOption(page, examOptionInfo);
    }
}
