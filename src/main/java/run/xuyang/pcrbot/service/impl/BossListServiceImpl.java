package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.BossList;
import run.xuyang.pcrbot.mapper.BossListMapper;
import run.xuyang.pcrbot.service.BossListService;

/**
 * 公会战boss列表业务层接口实现类
 *
 * @author XuYang
 * @date 2020/6/4 13:31
 */
@Service
public class BossListServiceImpl implements BossListService {

    private final BossListMapper bossListMapper;

    public BossListServiceImpl(BossListMapper bossListMapper) {
        this.bossListMapper = bossListMapper;
    }

    @Override
    public BossList findBossListByGroupID(long groupID) {
        return bossListMapper.findBossListByGroupID(groupID);
    }

    @Override
    public void deleteAllByGroupID(long groupID) {
        bossListMapper.deleteAllByGroupID(groupID);
    }

    @Override
    public void addOneBossListByGroup(long groupID) {
        bossListMapper.addOneBossListByGroup(groupID);
    }

    @Override
    public void updateBoss1Status(long groupID, int bossRemainHP, int nextOne) {
        bossListMapper.updateBoss1Status(groupID, bossRemainHP, nextOne);
    }

    @Override
    public void updateBoss2Status(long groupID, int bossRemainHP, int nextOne) {
        bossListMapper.updateBoss2Status(groupID, bossRemainHP, nextOne);
    }

    @Override
    public void updateBoss3Status(long groupID, int bossRemainHP, int nextOne) {
        bossListMapper.updateBoss3Status(groupID, bossRemainHP, nextOne);
    }

    @Override
    public void updateBoss4Status(long groupID, int bossRemainHP, int nextOne) {
        bossListMapper.updateBoss4Status(groupID, bossRemainHP, nextOne);
    }

    @Override
    public void updateBoss5StatusWithRounds(long groupID, int bossRemainHP, int nextOne, int nextRounds) {
        bossListMapper.updateBoss5StatusWithRounds(groupID, bossRemainHP, nextOne, nextRounds);
    }

    @Override
    public void resetAllBossRemainHP(int boss1Hp, int boss2Hp, int boss3Hp, int boss4Hp, int boss5Hp, long groupID) {
        bossListMapper.resetAllBossRemainHP(boss1Hp, boss2Hp, boss3Hp, boss4Hp, boss5Hp, groupID);
    }

    @Override
    public void updateBoss1StatusWithRounds(long groupID, int curHP, int curRounds, int curWhichOne) {
        bossListMapper.updateBoss1StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
    }

    @Override
    public void updateBoss2StatusWithRounds(long groupID, int curHP, int curRounds, int curWhichOne) {
        bossListMapper.updateBoss2StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
    }

    @Override
    public void updateBoss3StatusWithRounds(long groupID, int curHP, int curRounds, int curWhichOne) {
        bossListMapper.updateBoss3StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
    }

    @Override
    public void updateBoss4StatusWithRounds(long groupID, int curHP, int curRounds, int curWhichOne) {
        bossListMapper.updateBoss4StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
    }

}
