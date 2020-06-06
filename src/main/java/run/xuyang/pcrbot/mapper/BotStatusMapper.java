package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import run.xuyang.pcrbot.entity.BotStatus;

/**
 * 机器人状态信息持久层接口
 *
 * @author XuYang
 * @date 2020/6/3 15:08
 */
public interface BotStatusMapper {

    /**
     * 根据群号查询该群机器人是否启用
     *
     * @param groupID 群号
     * @return 机器人状态对象
     */
    @Select("select * from bot_status where group_id=#{groupID}")
    @Results(id = "botMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class),
            @Result(column = "group_bot_status", property = "groupBotStatus", javaType = Integer.class)
    })
    BotStatus findGroupByGroupID(Long groupID);

    /**
     * 将机器人添加到一个新的群里
     *
     * @param groupID 群号
     * @param status  该群机器人状态
     */
    @Insert("insert into bot_status(group_id, group_bot_status) values(#{groupID}, #{status})")
    void addBotToGroup(long groupID, int status);

    /**
     * 更新指定群号的机器人的状态
     *
     * @param groupID 群号
     * @param status  该群机器人状态
     */
    @Update("update bot_status set group_bot_status=#{status} where group_id=#{groupID}")
    void updateBotByGroupID(long groupID, int status);
}
