package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExamOptionInfo;
import cc.mrbird.febs.cos.entity.ExamPaperInfo;
import cc.mrbird.febs.cos.dao.ExamPaperInfoMapper;
import cc.mrbird.febs.cos.entity.ExamResultInfo;
import cc.mrbird.febs.cos.service.IExamOptionInfoService;
import cc.mrbird.febs.cos.service.IExamPaperInfoService;
import cc.mrbird.febs.cos.service.IExamResultInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamPaperInfoServiceImpl extends ServiceImpl<ExamPaperInfoMapper, ExamPaperInfo> implements IExamPaperInfoService {

    private final IExamOptionInfoService examOptionInfoService;

    private final IExamResultInfoService examResultInfoService;

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

    /**
     * 导入专家信息列表
     *
     * @param file 文件
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importExcel(MultipartFile file) throws Exception {
        ExcelReader excelReader1 = ExcelUtil.getReader(file.getInputStream(), 0);
        ExcelReader excelReader2 = ExcelUtil.getReader(file.getInputStream(), 1);
        ExcelReader excelReader3 = ExcelUtil.getReader(file.getInputStream(), 2);
        setExcelHeaderAlias1(excelReader1);
        setExcelHeaderAlias2(excelReader2);
        setExcelHeaderAlias3(excelReader3);
        List<ExamPaperInfo> reports1 = excelReader1.read(1, 2, Integer.MAX_VALUE, ExamPaperInfo.class);
        List<ExamResultInfo> reports2 = excelReader2.read(1, 2, Integer.MAX_VALUE, ExamResultInfo.class);
        List<ExamOptionInfo> reports3 = excelReader3.read(1, 2, Integer.MAX_VALUE, ExamOptionInfo.class);
        StringBuilder error = new StringBuilder("");
        if (CollectionUtil.isEmpty(reports1) || CollectionUtil.isEmpty(reports2) || CollectionUtil.isEmpty(reports3)) {
            error.append("导入数据不得为空。");
            return error.toString();
        }
        ExamPaperInfo examPaperInfo = reports1.get(0);
        examPaperInfo.setCode("EX-" + System.currentTimeMillis());
        examPaperInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        examPaperInfo.setStatus("1");
        this.save(examPaperInfo);

        for (ExamResultInfo examResultInfo : reports2) {
            examResultInfo.setPaperId(examPaperInfo.getId());
        }

        int indexNo = 0;
        for (ExamOptionInfo examOptionInfo : reports3) {
            indexNo ++;
            examOptionInfo.setPaperId(examPaperInfo.getId());
            examOptionInfo.setIndexNo(indexNo);
            if (StrUtil.isNotEmpty(examOptionInfo.getType()) && "多结果单选".equals(examOptionInfo.getType())) {
                examOptionInfo.setType("1");
            }
            if (StrUtil.isNotEmpty(examOptionInfo.getType()) && "单结果单选".equals(examOptionInfo.getType())) {
                examOptionInfo.setType("2");
            }
        }

        examOptionInfoService.saveBatch(reports3);
        examResultInfoService.saveBatch(reports2);

        if (StrUtil.isEmpty(error.toString())) {
            return null;
        }
        return error.toString();
    }

    /**
     * 试卷信息详情
     *
     * @param id 试卷选项ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> detail(Integer id) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("examPaper", null);
                put("result", Collections.emptyList());
                put("option", Collections.emptyList());
            }
        };

        ExamPaperInfo examPaperInfo = this.getById(id);
        result.put("examPaper", examPaperInfo);
        List<ExamOptionInfo> examOptionInfoList = examOptionInfoService.list(Wrappers.<ExamOptionInfo>lambdaQuery().eq(ExamOptionInfo::getPaperId, examPaperInfo.getId()));
        result.put("option", examOptionInfoList);
        List<ExamResultInfo> examResultInfoList = examResultInfoService.list(Wrappers.<ExamResultInfo>lambdaQuery().eq(ExamResultInfo::getPaperId, examPaperInfo.getId()));
        result.put("result", examResultInfoList);
        return result;
    }

    /**
     * 设置HeaderAlias
     *
     * @param excelReader HeaderAlias
     */
    public void setExcelHeaderAlias1(ExcelReader excelReader) {
        excelReader.addHeaderAlias("试卷名称", "exampaperName");
        excelReader.addHeaderAlias("创建人", "createBy");
        excelReader.addHeaderAlias("备注", "content");
    }

    /**
     * 设置HeaderAlias
     *
     * @param excelReader HeaderAlias
     */
    public void setExcelHeaderAlias2(ExcelReader excelReader) {
        excelReader.addHeaderAlias("分数开始", "scoreStart");
        excelReader.addHeaderAlias("分数结束", "scoreEnd");
        excelReader.addHeaderAlias("检测结果", "title");
    }

    /**
     * 设置HeaderAlias
     *
     * @param excelReader HeaderAlias
     */
    public void setExcelHeaderAlias3(ExcelReader excelReader) {
        excelReader.addHeaderAlias("试题名称", "title");
        excelReader.addHeaderAlias("选项", "contentOptions");
        excelReader.addHeaderAlias("分数", "score");
        excelReader.addHeaderAlias("正确答案", "answer");
        excelReader.addHeaderAlias("答案解析", "analysis");
        excelReader.addHeaderAlias("试题类型", "type");
    }
}
