package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ArticleInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IArticleInfoService extends IService<ArticleInfo> {

    /**
     * 分页获取文章信息
     *
     * @param page        分页对象
     * @param articleInfo 文章信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectArticlePage(Page<ArticleInfo> page, ArticleInfo articleInfo);
}
