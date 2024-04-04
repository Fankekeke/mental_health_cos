package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.TeacherInfo;
import cc.mrbird.febs.cos.dao.TeacherInfoMapper;
import cc.mrbird.febs.cos.service.ITeacherInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements ITeacherInfoService {

    /**
     * 分页获取教师信息
     *
     * @param page        分页对象
     * @param teacherInfo 教师信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectTeacherPage(Page<TeacherInfo> page, TeacherInfo teacherInfo) {
        return baseMapper.selectTeacherPage(page, teacherInfo);
    }
}
