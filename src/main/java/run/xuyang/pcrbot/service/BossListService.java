package run.xuyang.pcrbot.service;

import run.xuyang.pcrbot.entity.BossList;

/**
 * 公会战boss列表业务层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:31
 */
public interface BossListService {

    /**
     * 查找指定公会的boss列表
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的boss列表
     */
    BossList findBossListByGroupID(long groupID);

    /**
     * 删除指定公会的boss列表
     *
     * @param groupID 公会所在QQ群号
     */
    void deleteAllByGroupID(long groupID);

    /**
     * 给指定公会开启会战boss列表
     *
     * @param groupID 公会所在QQ群号
     */
    void addOneBossListByGroup(long groupID);

    /**
     * 更新指定公会boss1的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    void updateBoss1Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss2的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    void updateBoss2Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss2的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    void updateBoss3Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss4的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    void updateBoss4Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss4的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     * @param nextRounds   下一周是多少
     */
    void updateBoss5StatusWithRounds(long groupID, int bossRemainHP, int nextOne, int nextRounds);

    /**
     * 重置下一周目所有boss的血量
     *
     * @param boss1Hp boss1血量
     * @param boss2Hp boss2血量
     * @param boss3Hp boss3血量
     * @param boss4Hp boss4血量
     * @param boss5Hp boss5血量
     * @param groupID 哪个公会的
     */
    void resetAllBossRemainHP(int boss1Hp, int boss2Hp, int boss3Hp, int boss4Hp, int boss5Hp, long groupID);

}
