package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ReplyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface ReplyInfoMapper extends BaseMapper<ReplyInfo> {

    /**
     * 分页获取消息回复信息
     *
     * @param page      分页对象
     * @param replyInfo 消息回复信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectReplyPage(Page<ReplyInfo> page, @Param("replyInfo") ReplyInfo replyInfo);

    // 获取具体的帖子回复信息
    List<LinkedHashMap<String, Object>> replyListByPostId(@Param("postId") Integer postId);
}
