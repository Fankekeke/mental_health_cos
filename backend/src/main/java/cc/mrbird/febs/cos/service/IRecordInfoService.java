package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IRecordInfoService extends IService<RecordInfo> {

    /**
     * 分页获取考试记录信息
     *
     * @param page       分页对象
     * @param recordInfo 考试记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<RecordInfo> page, RecordInfo recordInfo);

    /**
     * 查询主页信息
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();
}
