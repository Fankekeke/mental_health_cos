package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.StudentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    /**
     * 分页获取学生信息
     *
     * @param page        分页对象
     * @param studentInfo 学生信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectStudentPage(Page<StudentInfo> page, @Param("studentInfo") StudentInfo studentInfo);
}
