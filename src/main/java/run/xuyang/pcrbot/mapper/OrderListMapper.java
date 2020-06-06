package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import run.xuyang.pcrbot.entity.OrderList;

import java.util.List;

/**
 * 预约出刀列表持久层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:36
 */
public interface OrderListMapper {

    /**
     * 查询指定公会的预约出刀记录列表
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的所有预约出刀记录
     */
    @Select("select * from order_list where group_id=#{groupID}")
    @Results(id = "orderListMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "member_id", property = "memberID", javaType = Long.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class),
            @Result(column = "which_one", property = "whichOne", javaType = Integer.class),
            @Result(column = "damage", property = "damage", javaType = Integer.class),
            @Result(column = "rounds", property = "rounds", javaType = Integer.class)
    })
    List<OrderList> findAllOrderListByGroup(long groupID);

    /**
     * 删除指定公会的所有出刀记录
     *
     * @param groupID 公会所在QQ群号
     */
    @Delete("delete from order_list where group_id=#{groupID}")
    void deleteAllByGroupID(long groupID);

    /**
     * 查询指定公会当前的预约boss列表
     *
     * @param groupID  公会所在QQ群号
     * @param whichOne 预约的哪一个boss
     * @param rounds   预约的第几周目的boss
     * @return 该公会当前的预约boss列表
     */
    @Select("select * from order_list where group_id=#{groupID} and which_one=#{whichOne} and rounds=#{rounds}")
    @ResultMap("orderListMap")
    List<OrderList> findCurrentOrderList(long groupID, int whichOne, int rounds);

    /**
     * 预约rounds周目的第whichOne个boss，预估伤害位damage
     *
     * @param userID   预约的用户QQ号
     * @param groupID  预约用户所在公会QQ群号
     * @param whichOne 预约哪一个boss
     * @param damage   预估伤害
     * @param rounds   预约的boss是当前周目还是下一周目的
     */
    @Insert("insert into order_list(member_id, group_id, which_one, damage, rounds) " +
            "values(#{userID}, #{groupID}, #{whichOne}, #{damage}, #{rounds})")
    void addOrder(long userID, long groupID, int whichOne, int damage, int rounds);

    /**
     * 查询指定的成员是否预约了当前的boss
     *
     * @param userID   查询的成员QQ号
     * @param groupID  该成员所在公会群QQ号
     * @param whichOne 当前公会的boss
     * @param rounds   当前boss的周目数
     * @return 预约了当前boss就返回预约对象，否则返回null
     */
    @Select("select * from order_list where member_id=#{userID} " +
            "and group_id=#{groupID} " +
            "and which_one=#{whichOne} " +
            "and rounds=#{rounds}")
    @ResultMap("orderListMap")
    List<OrderList> findCurrentOrderListByUserID(long userID, long groupID, int whichOne, int rounds);

    /**
     * 删除指定成员最早的一条出刀记录
     *
     * @param userID 成员QQ号
     */
    @Delete("delete from order_list where member_id=#{userID} and group_id=#{groupID} " +
            "order by id asc " +
            "limit 1;")
    void deleteEarliestOneByUserID(long userID, long groupID);

    /**
     * 查询指定成员的所有预约记录
     *
     * @param userID 成员QQ号
     * @return 该成员的所有预约记录
     */
    @Select("select * from order_list where member_id=#{userID} and group_id=#{groupID}")
    @ResultMap("orderListMap")
    List<OrderList> findAllOrderListByUserID(long userID, long groupID);

    /**
     * 删除指定成员的所有出刀记录
     *
     * @param userID 成员QQ号
     */
    @Delete("delete from order_list where member_id=#{userID} and group_id=#{groupID}")
    void deleteAllByUserID(long userID, long groupID);
}
