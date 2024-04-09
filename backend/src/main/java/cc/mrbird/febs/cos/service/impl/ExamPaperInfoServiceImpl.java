package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.RecordInfoMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.ExamPaperInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamPaperInfoServiceImpl extends ServiceImpl<ExamPaperInfoMapper, ExamPaperInfo> implements IExamPaperInfoService {

    private final IExamOptionInfoService examOptionInfoService;

    private final IExamResultInfoService examResultInfoService;

    private final IStudentInfoService studentInfoService;

    @Lazy
    private final RecordInfoMapper recordInfoMapper;

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
     * 导入试卷信息列表
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
     * 试卷答题提交
     *
     * @param option 答题信息
     * @return 结构
     */
    @Override
    public boolean examTestCommit(String option, String userId) {
        // 答题信息
        List<ExamOptionInfo> optionInfoList = JSONUtil.toList(option, ExamOptionInfo.class);

        // 试题信息
        ExamPaperInfo examPaperInfo = this.getById(optionInfoList.get(0).getPaperId());

        // 试题结果
        List<ExamResultInfo> examResultList = examResultInfoService.list(Wrappers.<ExamResultInfo>lambdaQuery().eq(ExamResultInfo::getPaperId, examPaperInfo.getId()));

        // 总分数
        int total = 0;

        for (ExamOptionInfo examOptionInfo : optionInfoList) {
            List<String> contentOptionsList = Arrays.asList(StrUtil.split(examOptionInfo.getContentOptions(), ","));

            List<String> scoreList = Arrays.asList(StrUtil.split(examOptionInfo.getScore(), ","));

            if (StrUtil.isNotEmpty(examOptionInfo.getPickCheck())) {
                if ("1".equals(examOptionInfo.getType())) {

                    for (int i = 0; i < contentOptionsList.size(); i++) {
                        if (contentOptionsList.get(i).equals(examOptionInfo.getPickCheck())) {
                            total += Integer.parseInt(scoreList.get(i));
                        }
                    }

                } else {
                    if (examOptionInfo.getPickCheck().equals(examOptionInfo.getAnswer())) {
                        total += Integer.parseInt(examOptionInfo.getScore());
                    } else {
                        total += 0;
                    }
                }
            }
        }

        // 学生信息
        StudentInfo studentInfo = studentInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, userId));

        // 结果
        String result = "";
        for (ExamResultInfo examResultInfo : examResultList) {
            if (examResultInfo.getScoreStart() <= total && examResultInfo.getScoreEnd() > total) {
                result = examResultInfo.getTitle();
            }
        }

        // 答题结果
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setStudentId(studentInfo.getId());
        recordInfo.setExamId(examPaperInfo.getId());
        recordInfo.setScore(BigDecimal.valueOf(total));
        recordInfo.setEndDate(DateUtil.formatDateTime(new Date()));
        recordInfo.setRemark(result);
        return recordInfoMapper.insert(recordInfo) >= 1;
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
