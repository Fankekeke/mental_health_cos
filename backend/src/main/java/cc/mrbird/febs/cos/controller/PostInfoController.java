package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.cos.service.IPostInfoService;
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
@RequestMapping("/cos/post-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostInfoController {

    private final IPostInfoService postInfoService;

    /**
     * 分页获取贴子信息
     *
     * @param page     分页对象
     * @param postInfo 贴子信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PostInfo> page, PostInfo postInfo) {
        return R.ok(postInfoService.selectPostPage(page, postInfo));
    }

    /**
     * 贴子信息详情
     *
     * @param id 贴子ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(postInfoService.getById(id));
    }

    /**
     * 贴子信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(postInfoService.list());
    }

    /**
     * 新增贴子信息
     *
     * @param postInfo 贴子信息
     * @return 结果
     */
    @PostMapping
    public R save(PostInfo postInfo) {
        postInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(postInfoService.save(postInfo));
    }

    /**
     * 修改贴子信息
     *
     * @param postInfo 贴子信息
     * @return 结果
     */
    @PutMapping
    public R edit(PostInfo postInfo) {
        return R.ok(postInfoService.updateById(postInfo));
    }

    /**
     * 删除贴子信息
     *
     * @param ids ids
     * @return 贴子信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(postInfoService.removeByIds(ids));
    }
}
