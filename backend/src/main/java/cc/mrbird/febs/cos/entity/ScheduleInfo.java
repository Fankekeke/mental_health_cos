package cc.mrbird.febs.cos.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 排班信息
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScheduleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String code;

    /**
     * 排班名称
     */
    private String name;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 所属员工
     */
    private String staffIds;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 排班日期
     */
    private String taskDate;

    /**
     * 是否为模板 0.否 1.是
     */
    private String status;

    /**
     * 标识 0.未预约 1.已预约
     */
    private String checkFlag;


}
