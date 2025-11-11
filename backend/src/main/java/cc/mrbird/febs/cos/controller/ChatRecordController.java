package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ChatRecord;
import cc.mrbird.febs.cos.service.IChatRecordService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/chat-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatRecordController {

    private final IChatRecordService chatRecordService;

    /**
     * 分页查询聊天记录
     *
     * @param page       分页对象
     * @param chatRecord 聊天记录
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ChatRecord> page, ChatRecord chatRecord) {
        return R.ok(chatRecordService.page(page, Wrappers.<ChatRecord>lambdaQuery()
                .eq(chatRecord.getUserId() != null, ChatRecord::getUserId, chatRecord.getUserId())
                .eq(chatRecord.getHotelId() != null, ChatRecord::getHotelId, chatRecord.getHotelId())
                .orderByDesc(ChatRecord::getCreateTime)));
    }

    /**
     * 根据教师ID获取沟通联系人列表
     *
     * @param hotelId 教师ID
     * @return 联系人列表
     */
    @GetMapping("/contacts/hotel/{hotelId}")
    public R getContactsByHotelId(@PathVariable Integer hotelId) {
        return R.ok(chatRecordService.getContactsByHotelId(hotelId));
    }

    /**
     * 根据用户ID获取沟通联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    @GetMapping("/contacts/user/{userId}")
    public R getContactsByUserId(@PathVariable Integer userId) {
        return R.ok(chatRecordService.getContactsByUserId(userId));
    }

    /**
     * 根据用户ID和教师ID获取聊天记录
     *
     * @param userId  用户ID
     * @param hotelId 教师ID
     * @return 结果
     */
    @GetMapping("/list")
    public R getListByUserAndHotel(@RequestParam Integer userId, @RequestParam Integer hotelId) {
        List<ChatRecord> list = chatRecordService.list(Wrappers.<ChatRecord>lambdaQuery()
                .eq(ChatRecord::getUserId, userId)
                .eq(ChatRecord::getHotelId, hotelId)
                .orderByAsc(ChatRecord::getCreateTime));
        return R.ok(list);
    }

    /**
     * 发送消息
     *
     * @param chatRecord 聊天记录
     * @return 结果
     */
    @PostMapping
    public R sendMsg(@RequestBody ChatRecord chatRecord) {
        return R.ok(chatRecordService.save(chatRecord));
    }

    /**
     * 标记消息为已读
     *
     * @param id 消息ID
     * @return 结果
     */
    @PutMapping("/read/{id}")
    public R markAsRead(@PathVariable Integer id) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setId(id);
        chatRecord.setStatus("1");
        return R.ok(chatRecordService.updateById(chatRecord));
    }
}
