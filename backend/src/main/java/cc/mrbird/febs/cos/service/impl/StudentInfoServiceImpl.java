package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StudentInfo;
import cc.mrbird.febs.cos.mapper.StudentInfoMapper;
import cc.mrbird.febs.cos.service.IStudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

}
