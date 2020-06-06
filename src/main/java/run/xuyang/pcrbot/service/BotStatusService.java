package run.xuyang.pcrbot.service;

import run.xuyang.pcrbot.entity.BotStatus;

/**
 * 机器人状态业务接口
 *
 * @author XuYang
 * @date 2020/6/3 20:26
 */
public interface BotStatusService {

    /**
     * 根据群号查询该群机器人是否启用
     *
     * @param groupID 群号
     * @return 机器人状态对象
     */
    BotStatus findGroupByGroupID(Long groupID);

    /**
     * 将机器人添加到一个新的群里
     *
     * @param groupID 群号
     * @param status  该群机器人状态
     */
    void addBotToGroup(long groupID, int status);

    /**
     * 更新指定群号的机器人的状态
     *
     * @param groupID 群号
     * @param status  该群机器人状态
     */
    void updateBotByGroupID(long groupID, int status);
}
