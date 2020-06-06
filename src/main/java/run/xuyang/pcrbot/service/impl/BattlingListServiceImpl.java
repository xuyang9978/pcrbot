package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.BattlingList;
import run.xuyang.pcrbot.mapper.BattlingListMapper;
import run.xuyang.pcrbot.service.BattlingListService;

import java.util.List;

/**
 * 正在出刀成员列表业务层接口实现类
 *
 * @author XuYang
 * @date 2020/6/4 17:15
 */
@Service
public class BattlingListServiceImpl implements BattlingListService {

    private final BattlingListMapper battlingListMapper;

    public BattlingListServiceImpl(BattlingListMapper battlingListMapper) {
        this.battlingListMapper = battlingListMapper;
    }

    @Override
    public List<BattlingList> findBattlingListByGroupID(long groupID) {
        return battlingListMapper.findBattlingListByGroupID(groupID);
    }

    @Override
    public void deleteAllBattlingListByGroupID(long groupID) {
        battlingListMapper.deleteAllBattlingListByGroupID(groupID);
    }

    @Override
    public BattlingList findBattlingListByUserID(long userID, long groupID) {
        return battlingListMapper.findBattlingListByUserID(userID, groupID);
    }

    @Override
    public void addBattling(long userID, long groupID, int whichOne, int rounds) {
        battlingListMapper.addBattling(userID, groupID, whichOne, rounds);
    }

    @Override
    public void deleteBattling(long userID, long groupID) {
        battlingListMapper.deleteBattling(userID, groupID);
    }
}
