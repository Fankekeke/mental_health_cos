package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.RecordInfo;
import cc.mrbird.febs.cos.dao.RecordInfoMapper;
import cc.mrbird.febs.cos.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecordInfoServiceImpl extends ServiceImpl<RecordInfoMapper, RecordInfo> implements IRecordInfoService {

    private final IStudentInfoService studentInfoService;

    private final ITeacherInfoService teacherInfoService;

    private final IPostInfoService postInfoService;

    private final IExamPaperInfoService excelPaperInfoService;

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取考试记录信息
     *
     * @param page       分页对象
     * @param recordInfo 考试记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<RecordInfo> page, RecordInfo recordInfo) {
        return baseMapper.selectRecordPage(page, recordInfo);
    }

    @Override
    public LinkedHashMap<String, Object> homeData() {
        // 管理员展示信息
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        // 本月收益
        BigDecimal incomeMonth = baseMapper.selectIncomeMonth();
        // 本月工单
        Integer workOrderMonth = baseMapper.selectWorkOrderMonth();
        // 本年收益
        BigDecimal incomeYear = baseMapper.selectIncomeYear();
        // 本年工单
        Integer workOrderYear = baseMapper.selectWorkOrderYear();
        // 学生数量
        Integer studentNum = studentInfoService.count();
        // 试卷数量
        Integer examNum = excelPaperInfoService.count();
        // 教师数量
        Integer teacherNum = teacherInfoService.count();
        // 答题数量
        Integer examRecordNum = this.count();
        // 十天内缴费记录
        List<LinkedHashMap<String, Object>> paymentRecord = baseMapper.selectPaymentRecord();
        // 十天内工单数量
        List<LinkedHashMap<String, Object>> orderRecord = baseMapper.selectOrderRecord();
        result.put("incomeMonth", incomeMonth);
        result.put("workOrderMonth", workOrderMonth);
        result.put("incomeYear", incomeYear);
        result.put("workOrderYear", workOrderYear);
        result.put("studentNum", studentNum);
        result.put("examNum", examNum);
        result.put("teacherNum", teacherNum);
        result.put("examRecordNum", examRecordNum);
        result.put("paymentRecord", paymentRecord);
        result.put("orderRecord", orderRecord);

        // 公告信息
        List<BulletinInfo> bulletinList = bulletinInfoService.list();
        result.put("bulletin", bulletinList);
        return result;
    }
}
