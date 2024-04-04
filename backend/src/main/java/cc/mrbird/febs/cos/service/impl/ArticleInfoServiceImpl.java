package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ArticleInfo;
import cc.mrbird.febs.cos.dao.ArticleInfoMapper;
import cc.mrbird.febs.cos.service.IArticleInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class ArticleInfoServiceImpl extends ServiceImpl<ArticleInfoMapper, ArticleInfo> implements IArticleInfoService {

    /**
     * 分页获取文章信息
     *
     * @param page        分页对象
     * @param articleInfo 文章信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectArticlePage(Page<ArticleInfo> page, ArticleInfo articleInfo) {
        return baseMapper.selectArticlePage(page, articleInfo);
    }
}
