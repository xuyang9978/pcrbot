package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import org.omg.CORBA.INTERNAL;
import run.xuyang.pcrbot.entity.BattleLog;

import java.util.Date;
import java.util.List;

/**
 * 出刀记录持久层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:18
 */
public interface BattleLogMapper {

    /**
     * 查找指定公会的所有出刀记录
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会此次公会战的所有出刀记录
     */
    @Select("select * from battle_log where group_id=#{groupID}")
    @Results(id = "battlesLogMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "member_id", property = "memberID", javaType = Long.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class),
            @Result(column = "damage", property = "damage", javaType = Integer.class),
            @Result(column = "battle_date", property = "battleDate", javaType = Date.class),
            @Result(column = "which_one", property = "whichOne", javaType = Integer.class),
            @Result(column = "rounds", property = "rounds", javaType = Integer.class)
    })
    List<BattleLog> findBattleLogByGroupID(long groupID);

    /**
     * 删除指定公会的所有出刀记录
     *
     * @param groupID 公会所在QQ群号
     */
    @Delete("delete from battle_log where group_id = #{groupID}")
    void deleteAllByGroupID(long groupID);

    /**
     * 查询指定公会当天当前周目当前boss的出刀记录
     *
     * @param groupID  公会所在QQ群号
     * @param whichOne 老几
     * @param rounds   第几周目
     * @return 该公会当天当前周目当前boss的出刀记录
     */
    @Select("select * from battle_log where group_id=#{groupID} and which_one=#{whichOne} and rounds=#{rounds}")
    @ResultMap("battlesLogMap")
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
    @Insert("insert into battle_log(member_id, group_id, damage, battle_date, which_one, rounds)" +
            "values(#{userID}, #{groupID}, #{damage}, #{battleDate}, #{whichOne}, #{rounds})")
    void addBattleLog(long userID, long groupID, int damage, Date battleDate, int whichOne, int rounds);

    /**
     * 查询指定成员当天的出刀次数
     * 实现方法是：拿到当前时间减去出刀时间小于24小时的就是今天的出刀记录
     *
     * @param userID 成员QQ号
     * @return 该成员当前的出刀次数
     */
    @Select("select count(*) from battle_log " +
            "where battle_date " +
            "between date_add(from_unixtime(unix_timestamp(cast(sysdate()as date))), interval 4 hour) " +
            "and date_add(from_unixtime(unix_timestamp(cast(sysdate()as date) + interval 1 day)), interval 4 hour) " +
            "and member_id=#{userID} " +
            "and group_id=#{groupID}")
    int countDayLogByUserID(long userID, long groupID);

    /**
     * 查询指定成员当天的所有出刀记录
     *
     * @param memberID 成员QQ号
     * @return 该成员当天的出刀记录
     */
    @Select("select * from battle_log " +
            "where battle_date " +
            "between date_add(from_unixtime(unix_timestamp(cast(sysdate()as date))), interval 4 hour) " +
            "and date_add(from_unixtime(unix_timestamp(cast(sysdate()as date) + interval 1 day)), interval 4 hour) " +
            "and member_id=#{memberID} " +
            "and group_id=#{groupID}")
    @ResultMap("battlesLogMap")
    List<BattleLog> findTodayBattleLogByUserID(Long memberID, long groupID);

    /**
     * 查询指定成员的所有出刀记录
     *
     * @param userID 成员QQ号
     * @return 该成员的所有出刀记录
     */
    @Select("select * from battle_log where member_id=#{userID} and group_id=#{groupID}")
    @ResultMap("battlesLogMap")
    List<BattleLog> findAllBattleLogByUserID(Long userID, long groupID);
}
