package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExamPaperInfo;
import cc.mrbird.febs.cos.dao.ExamPaperInfoMapper;
import cc.mrbird.febs.cos.service.IExamPaperInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class ExamPaperInfoServiceImpl extends ServiceImpl<ExamPaperInfoMapper, ExamPaperInfo> implements IExamPaperInfoService {

    /**
     * 分页获取试卷信息
     *
     * @param page          分页对象
     * @param examPaperInfo 试卷信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectExamPaperPage(Page<ExamPaperInfo> page, ExamPaperInfo examPaperInfo) {
        return baseMapper.selectExamPaperPage(page, examPaperInfo);
    }
}
