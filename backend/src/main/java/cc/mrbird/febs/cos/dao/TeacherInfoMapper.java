package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.TeacherInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {

    /**
     * 分页获取教师信息
     *
     * @param page        分页对象
     * @param teacherInfo 教师信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectTeacherPage(Page<TeacherInfo> page, @Param("teacherInfo") TeacherInfo teacherInfo);
}
