package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 试卷选项
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExamOptionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属试卷
     */
    private Integer paperId;

    /**
     * 试题名称
     */
    private String title;

    /**
     * 选项
     */
    private String contentOptions;

    /**
     * 分数
     */
    private String score;

    /**
     * 正确答案
     */
    private String answer;

    /**
     * 答案解析
     */
    private String analysis;

    /**
     * 试题类型
     */
    private String type;

    /**
     * 序号
     */
    private Integer indexNo;

    @TableField(exist = false)
    private String pickCheck;

}
