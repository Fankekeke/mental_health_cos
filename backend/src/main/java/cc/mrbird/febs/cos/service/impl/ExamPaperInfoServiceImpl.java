package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExamPaperInfo;
import cc.mrbird.febs.cos.dao.ExamPaperInfoMapper;
import cc.mrbird.febs.cos.service.IExamPaperInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 导入专家信息列表
     *
     * @param file 文件
     * @return 结果
     */
    @Override
    public String importExcel(MultipartFile file) throws Exception {
//        ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream(), 0);
//        setExcelHeaderAlias(excelReader);
//        List<ExpertInfo> reports = excelReader.read(1, 2, Integer.MAX_VALUE, ExpertInfo.class);
//        StringBuilder error = new StringBuilder("");
//        if (CollectionUtil.isEmpty(reports)) {
//            error.append("导入数据不得为空。");
//            return error.toString();
//        }
//        for (ExpertInfo expert : reports) {
//            if (StrUtil.isEmpty(expert.getName())) {
//                error.append("\n名称不能为空");
//                return error.toString();
//            }
//            expert.setCode("EX-" + System.currentTimeMillis());
//            expert.setOpenFlag(1);
//            expert.setHasExist(0);
//            expert.setCreateDate(DateUtil.formatDate(new Date()));
//        }
//        if (StrUtil.isEmpty(error.toString())) {
//            this.saveBatch(reports);
//            return null;
//        }
//        return error.toString();
        return null;
    }
}
