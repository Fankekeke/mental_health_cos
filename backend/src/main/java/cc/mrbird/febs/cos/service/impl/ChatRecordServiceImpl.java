package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ChatRecord;
import cc.mrbird.febs.cos.dao.ChatRecordMapper;
import cc.mrbird.febs.cos.service.IChatRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements IChatRecordService {

    /**
     * 根据教师ID获取沟通联系人列表
     *
     * @param hotelId 教师ID
     * @return 联系人列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> getContactsByHotelId(Integer hotelId) {
        return baseMapper.getContactsByHotelId(hotelId);
    }

    /**
     * 根据用户ID获取沟通联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    @Override
    public List<LinkedHashMap<String, Object>> getContactsByUserId(Integer userId) {
        return baseMapper.getContactsByUserId(userId);
    }
}
