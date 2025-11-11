package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ChatRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IChatRecordService extends IService<ChatRecord> {

    /**
     * 根据教师ID获取沟通联系人列表
     *
     * @param hotelId 教师ID
     * @return 联系人列表
     */
    List<LinkedHashMap<String, Object>> getContactsByHotelId(Integer hotelId);

    /**
     * 根据用户ID获取沟通联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    List<LinkedHashMap<String, Object>> getContactsByUserId(Integer userId);
}
