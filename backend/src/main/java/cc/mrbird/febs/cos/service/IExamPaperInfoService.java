package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ExamPaperInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IExamPaperInfoService extends IService<ExamPaperInfo> {

    /**
     * 分页获取试卷信息
     *
     * @param page          分页对象
     * @param examPaperInfo 试卷信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectExamPaperPage(Page<ExamPaperInfo> page, ExamPaperInfo examPaperInfo);

    /**
     * 导入专家信息列表
     *
     * @param file 文件
     * @return 结果
     */
    String importExcel(MultipartFile file) throws Exception;

    /**
     * 试卷信息详情
     *
     * @param id 试卷选项ID
     * @return 结果
     */
    LinkedHashMap<String, Object> detail(Integer id);
}
