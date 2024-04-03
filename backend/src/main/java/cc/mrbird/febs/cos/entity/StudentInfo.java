package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学生信息
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    private String code;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 学生头像
     */
    private String images;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


}
