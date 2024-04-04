package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PostInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface PostInfoMapper extends BaseMapper<PostInfo> {

    /**
     * 分页获取贴子信息
     *
     * @param page     分页对象
     * @param postInfo 贴子信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPostPage(Page<PostInfo> page, @Param("postInfo") PostInfo postInfo);
}
