package cc.mrbird.febs.cos.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 辅导预约记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 预约时间
     */
    private String registerDate;

    /**
     * 员工ID
     */
    private Integer staffId;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 状态（1.进行中 2.已完成 3.已退号）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 编号
     */
    private String code;

    /**
     * 辅导内容
     */
    private String reference;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 班次ID
     */
    private Integer scheduleId;

    @TableField(exist = false)
    private String scheduleName;

    @TableField(exist = false)
    private String teacherName;

    @TableField(exist = false)
    private String studentName;
}
