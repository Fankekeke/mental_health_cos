package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 考试记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 试卷ID
     */
    private Integer examId;

    /**
     * 分数
     */
    private BigDecimal score;

    /**
     * 结论
     */
    private String remark;

    /**
     * 考试结束时间
     */
    private String endDate;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String exampaperName;


}
