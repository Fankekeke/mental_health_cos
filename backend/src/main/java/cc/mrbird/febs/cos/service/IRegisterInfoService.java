package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RegisterInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IRegisterInfoService extends IService<RegisterInfo> {

    /**
     * 分页获取辅导预约信息
     *
     * @param page         分页对象
     * @param registerInfo 辅导预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Objects>> queryRegisterPage(Page<RegisterInfo> page, RegisterInfo registerInfo);
}
