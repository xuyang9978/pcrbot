package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.BattleLog;
import run.xuyang.pcrbot.mapper.BattleLogMapper;
import run.xuyang.pcrbot.service.BattleLogService;

import java.util.Date;
import java.util.List;

/**
 * 出刀记录业务层接口实现类
 *
 * @author XuYang
 * @date 2020/6/4 13:19
 */
@Service
public class BattleLogServiceImpl implements BattleLogService {

    private final BattleLogMapper battleLogMapper;

    public BattleLogServiceImpl(BattleLogMapper battleLogMapper) {
        this.battleLogMapper = battleLogMapper;
    }

    @Override
    public List<BattleLog> findBattleLogByGroupID(long groupID) {
        return battleLogMapper.findBattleLogByGroupID(groupID);
    }

    @Override
    public void deleteAllByGroupID(long groupID) {
        battleLogMapper.deleteAllByGroupID(groupID);
    }

    @Override
    public List<BattleLog> findCurrentBossBattleLog(long groupID, int whichOne, int rounds) {
        return battleLogMapper.findCurrentBossBattleLog(groupID, whichOne, rounds);
    }

    @Override
    public void addBattleLog(long userID, long groupID, int damage, Date battleDate, int whichOne, int rounds) {
        battleLogMapper.addBattleLog(userID, groupID, damage, battleDate, whichOne, rounds);
    }

    @Override
    public int countDayLogByUserID(long userID, long groupID) {
        return battleLogMapper.countDayLogByUserID(userID, groupID);
    }

    @Override
    public List<BattleLog> findTodayBattleLogByUserID(Long memberID, long groupID) {
        return battleLogMapper.findTodayBattleLogByUserID(memberID, groupID);
    }

    @Override
    public List<BattleLog> findAllBattleLogByUserID(Long userID, long groupID) {
        return battleLogMapper.findAllBattleLogByUserID(userID, groupID);
    }
}
