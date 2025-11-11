package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ChatRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {

    /**
     * 根据教师ID获取沟通联系人列表
     *
     * @param hotelId 教师ID
     * @return 联系人列表
     */
    List<LinkedHashMap<String, Object>> getContactsByHotelId(@Param("hotelId") Integer hotelId);

    /**
     * 根据用户ID获取沟通联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    List<LinkedHashMap<String, Object>> getContactsByUserId(@Param("userId") Integer userId);
}
