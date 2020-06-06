package run.xuyang.pcrbot.service;

import run.xuyang.pcrbot.entity.BattleLog;

import java.util.Date;
import java.util.List;

/**
 * 出刀记录业务层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:19
 */
public interface BattleLogService {

    /**
     * 查找指定公会的所有出刀记录
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会此次公会战的所有出刀记录
     */
    List<BattleLog> findBattleLogByGroupID(long groupID);

    /**
     * 删除指定公会的所有出刀记录
     *
     * @param groupID 公会所在QQ群号
     */
    void deleteAllByGroupID(long groupID);

    /**
     * 查询指定公会当天当前周目当前boss的出刀记录
     *
     * @param groupID  公会所在QQ群号
     * @param whichOne 老几
     * @param rounds   第几周目
     * @return 该公会当天当前周目当前boss的出刀记录
     */
    List<BattleLog> findCurrentBossBattleLog(long groupID, int whichOne, int rounds);

    /**
     * 增加一条出刀记录
     *
     * @param userID     出刀的成员QQ号
     * @param groupID    出刀成员所在公会QQ群号
     * @param damage     这一刀的伤害
     * @param battleDate 出刀时间
     * @param whichOne   打的哪个boss
     * @param rounds     是第几周目的boss
     */
    void addBattleLog(long userID, long groupID, int damage, Date battleDate, int whichOne, int rounds);

    /**
     * 查询指定成员当天的出刀次数
     *
     * @param userID  成员QQ号
     * @param groupID 成员所在公会QQ群号
     * @return 该成员当前的出刀次数
     */
    int countDayLogByUserID(long userID, long groupID);

    /**
     * 查询指定成员当天的所有出刀记录
     *
     * @param memberID 成员QQ号
     * @param groupID  成员所在公会
     * @return 该成员当天的出刀记录
     */
    List<BattleLog> findTodayBattleLogByUserID(Long memberID, long groupID);

    /**
     * 查询指定成员的所有出刀记录
     *
     * @param userID  成员QQ号
     * @param groupID 成员所在公会
     * @return 该成员的所有出刀记录
     */
    List<BattleLog> findAllBattleLogByUserID(Long userID, long groupID);
}
