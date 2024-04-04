package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ArticleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface ArticleInfoMapper extends BaseMapper<ArticleInfo> {

    /**
     * 分页获取文章信息
     *
     * @param page        分页对象
     * @param articleInfo 文章信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectArticlePage(Page<ArticleInfo> page, @Param("articleInfo") ArticleInfo articleInfo);
}
