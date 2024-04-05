package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ArticleInfo;
import cc.mrbird.febs.cos.service.IArticleInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/article-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleInfoController {

    private final IArticleInfoService articleInfoService;

    /**
     * 分页获取文章信息
     *
     * @param page        分页对象
     * @param articleInfo 文章信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ArticleInfo> page, ArticleInfo articleInfo) {
        return R.ok(articleInfoService.selectArticlePage(page, articleInfo));
    }

    /**
     * 文章信息详情
     *
     * @param id 文章ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(articleInfoService.getById(id));
    }

    /**
     * 文章信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(articleInfoService.list());
    }

    /**
     * 新增文章信息
     *
     * @param articleInfo 文章信息
     * @return 结果
     */
    @PostMapping
    public R save(ArticleInfo articleInfo) {
        articleInfo.setCode("ART-" + System.currentTimeMillis());
        articleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(articleInfoService.save(articleInfo));
    }

    /**
     * 修改文章信息
     *
     * @param articleInfo 文章信息
     * @return 结果
     */
    @PutMapping
    public R edit(ArticleInfo articleInfo) {
        return R.ok(articleInfoService.updateById(articleInfo));
    }

    /**
     * 删除文章信息
     *
     * @param ids ids
     * @return 文章信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(articleInfoService.removeByIds(ids));
    }
}
