package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RecordInfo;
import cc.mrbird.febs.cos.dao.RecordInfoMapper;
import cc.mrbird.febs.cos.service.IRecordInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class RecordInfoServiceImpl extends ServiceImpl<RecordInfoMapper, RecordInfo> implements IRecordInfoService {

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
}
