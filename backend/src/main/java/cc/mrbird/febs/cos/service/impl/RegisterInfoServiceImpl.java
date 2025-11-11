package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RegisterInfo;
import cc.mrbird.febs.cos.dao.RegisterInfoMapper;
import cc.mrbird.febs.cos.service.IRegisterInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class RegisterInfoServiceImpl extends ServiceImpl<RegisterInfoMapper, RegisterInfo> implements IRegisterInfoService {

    /**
     * 分页获取辅导预约信息
     *
     * @param page         分页对象
     * @param registerInfo 辅导预约信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Objects>> queryRegisterPage(Page<RegisterInfo> page, RegisterInfo registerInfo) {
        return baseMapper.queryRegisterPage(page, registerInfo);
    }
}
