package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.BotStatus;
import run.xuyang.pcrbot.mapper.BotStatusMapper;
import run.xuyang.pcrbot.service.BotStatusService;

/**
 * 机器人状态业务接口实现类
 *
 * @author XuYang
 * @date 2020/6/3 20:27
 */
@Service
public class BotStatusServiceImpl implements BotStatusService {

    private final BotStatusMapper botStatusMapper;

    public BotStatusServiceImpl(BotStatusMapper botStatusMapper) {
        this.botStatusMapper = botStatusMapper;
    }

    @Override
    public BotStatus findGroupByGroupID(Long groupID) {
        return botStatusMapper.findGroupByGroupID(groupID);
    }

    @Override
    public void addBotToGroup(long groupID, int status) {
        botStatusMapper.addBotToGroup(groupID, status);
    }

    @Override
    public void updateBotByGroupID(long groupID, int status) {
        botStatusMapper.updateBotByGroupID(groupID, status);
    }
}
