package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 聊天记录表
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 教师ID
     */
    private Integer teacherId;

    /**
     * 发送者类型 0-用户 1-教师
     */
    private String senderType;

    /**
     * 聊天内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 消息状态 0-未读 1-已读
     */
    private String status;

    /**
     * 删除标识 0-正常 1-已删除
     */
    private Boolean isDeleted;


}
