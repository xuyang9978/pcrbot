package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import org.omg.CORBA.INTERNAL;
import run.xuyang.pcrbot.entity.BattlingList;

import java.util.List;

/**
 * 正在出刀成员列表持久层接口
 *
 * @author XuYang
 * @date 2020/6/4 17:13
 */
public interface BattlingListMapper {

    /**
     * 查找指定公会的所有正在出刀记录
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会所有正在出刀记录
     */
    @Select("select * from battling_list where group_id=#{groupID}")
    @Results(id = "battlingListMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "member_id", property = "memberID", javaType = Long.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class),
            @Result(column = "which_one", property = "whichOne", javaType = Integer.class),
            @Result(column = "rounds", property = "rounds", javaType = Integer.class)
    })
    List<BattlingList> findBattlingListByGroupID(long groupID);

    /**
     * 删除指定公会的所有正在出刀记录
     *
     * @param groupID 公会所在QQ群号
     */
    @Delete("delete from battling_list where group_id=#{groupID}")
    void deleteAllBattlingListByGroupID(long groupID);

    /**
     * 查询指定成员是否正在出刀
     *
     * @param userID 查询的成员QQ号
     * @return 该成员是否正在出刀
     */
    @Select("select * from battling_list where member_id=#{userID} and group_id=#{groupID}")
    @ResultMap("battlingListMap")
    BattlingList findBattlingListByUserID(long userID, long groupID);

    /**
     * 添加一个成员正在出刀的记录
     *
     * @param userID   正在出刀成员的QQ号
     * @param groupID  正在出刀成员所在公会QQ群号
     * @param whichOne 正在打哪个boss
     * @param rounds   正在打的boss是第几周目的
     */
    @Insert("insert into battling_list(member_id, group_id, which_one, rounds)" +
            "values(#{userID}, #{groupID}, #{whichOne}, #{rounds})")
    void addBattling(long userID, long groupID, int whichOne, int rounds);

    /**
     * 删除成员正在出刀记录
     *
     * @param userID 要删除的成员QQ号
     */
    @Delete("delete from battling_list where member_id=#{userID} and group_id=#{groupID}")
    void deleteBattling(long userID, long groupID);
}
