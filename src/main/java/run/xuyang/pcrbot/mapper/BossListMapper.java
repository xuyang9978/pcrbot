package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import org.omg.CORBA.INTERNAL;
import run.xuyang.pcrbot.entity.BossList;

import java.util.Date;

/**
 * 公会战boss列表持久层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:30
 */
public interface BossListMapper {

    /**
     * 查找指定公会的boss列表
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的boss列表
     */
    @Select("select * from boss_list where group_id=#{groupID}")
    @Results(id = "bossListMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "rounds", property = "rounds", javaType = Integer.class),
            @Result(column = "boss1_hp", property = "boss1HP", javaType = Integer.class),
            @Result(column = "boss2_hp", property = "boss2HP", javaType = Integer.class),
            @Result(column = "boss3_hp", property = "boss3HP", javaType = Integer.class),
            @Result(column = "boss4_hp", property = "boss4HP", javaType = Integer.class),
            @Result(column = "boss5_hp", property = "boss5HP", javaType = Integer.class),
            @Result(column = "boss1_remain_hp", property = "boss1RemainHP", javaType = Integer.class),
            @Result(column = "boss2_remain_hp", property = "boss2RemainHP", javaType = Integer.class),
            @Result(column = "boss3_remain_hp", property = "boss3RemainHP", javaType = Integer.class),
            @Result(column = "boss4_remain_hp", property = "boss4RemainHP", javaType = Integer.class),
            @Result(column = "boss5_remain_hp", property = "boss5RemainHP", javaType = Integer.class),
            @Result(column = "rounds", property = "rounds", javaType = Integer.class),
            @Result(column = "which_one", property = "whichOne", javaType = Integer.class)
    })
    BossList findBossListByGroupID(long groupID);

    /**
     * 删除指定公会的boss列表
     *
     * @param groupID 公会所在QQ群号
     */
    @Delete("delete from boss_list where group_id=#{groupID}")
    void deleteAllByGroupID(long groupID);

    /**
     * 给指定公会开启会战boss列表
     *
     * @param groupID 公会所在QQ群号
     */
    @Insert("insert into boss_list(group_id) values(#{groupID})")
    void addOneBossListByGroup(long groupID);

    /**
     * 更新指定公会boss1的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    @Update("update boss_list set boss1_remain_hp=#{bossRemainHP}, which_one=#{nextOne} where group_id=#{groupID}")
    void updateBoss1Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss2的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    @Update("update boss_list set boss2_remain_hp=#{bossRemainHP}, which_one=#{nextOne} where group_id=#{groupID}")
    void updateBoss2Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss3的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    @Update("update boss_list set boss3_remain_hp=#{bossRemainHP}, which_one=#{nextOne} where group_id=#{groupID}")
    void updateBoss3Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss4的血量
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     */
    @Update("update boss_list set boss4_remain_hp=#{bossRemainHP}, which_one=#{nextOne} where group_id=#{groupID}")
    void updateBoss4Status(long groupID, int bossRemainHP, int nextOne);

    /**
     * 更新指定公会boss5的血量,并且重置12345的血量为初始值
     *
     * @param groupID      公会所在QQ群号
     * @param bossRemainHP boss当前剩余血量
     * @param nextOne      下一个boss是谁
     * @param nextRounds   下一周是多少
     */
    @Update("update boss_list set boss5_remain_hp=#{bossRemainHP}, which_one=#{nextOne}, rounds=#{nextRounds} where group_id=#{groupID}")
    void updateBoss5StatusWithRounds(long groupID, int bossRemainHP, int nextOne, int nextRounds);

    /**
     * 重置下一周目所有boss的血量
     *
     * @param boss1Hp boss1血量
     * @param boss2Hp boss2血量
     * @param boss3Hp boss3血量
     * @param boss4Hp boss4血量
     * @param boss5Hp boss5血量
     */
    @Update(" update boss_list set boss1_remain_hp=#{boss1Hp}, boss2_remain_hp=#{boss2Hp}, boss3_remain_hp=#{boss3Hp}, " +
            " boss4_remain_hp=#{boss4Hp}, boss5_remain_hp=#{boss5Hp} where group_id=#{groupID}")
    void resetAllBossRemainHP(int boss1Hp, int boss2Hp, int boss3Hp, int boss4Hp, int boss5Hp, long groupID);
}
