package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import run.xuyang.pcrbot.entity.TreeList;

import java.util.List;

/**
 * 挂树列表持久层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:41
 */
public interface TreeListMapper {

    /**
     * 查询指定公会所有挂树成员
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会所欲挂树成员
     */
    @Select("select * from tree_list where group_id=#{groupID}")
    @Results(id = "treeListMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "member_id", property = "memberID", javaType = Long.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class),
            @Result(column = "status", property = "status", javaType = Integer.class),
            @Result(column = "which_one", property = "whichOne", javaType = Integer.class),
            @Result(column = "rounds", property = "rounds", javaType = Integer.class)
    })
    List<TreeList> findAllTreeListByGroupID(long groupID);

    /**
     * 删除指定公会的所有挂树列表
     *
     * @param groupID 公会所在QQ群号
     */
    @Delete("delete from tree_list where group_id=#{groupID}")
    void deleteAllByGroupID(long groupID);

    /**
     * 查询指定公会当前的挂树列表
     *
     * @param groupID  公会所在QQ群号
     * @param whichOne 哪一个boss挂树
     * @param rounds   第几周目的boss挂树
     * @return 该公会当前的挂树列表
     */
    @Select("select * from tree_list where group_id=#{groupID} and which_one=#{whichOne} and rounds=#{rounds}")
    @ResultMap("treeListMap")
    List<TreeList> findCurrentTreeList(long groupID, int whichOne, int rounds);

    /**
     * 查询指定成员在当前boss是否挂树
     *
     * @param userID 查询的成员QQ号
     * @param status 挂树状态
     * @return 如果正在挂树中则返回该对象，否则返回null
     */
    @Select("select * from tree_list where member_id=#{userID} and group_id=#{groupID} and status=#{status}")
    @ResultMap("treeListMap")
    TreeList findCurrentTreeListByUserID(long userID, long groupID, int status);

    /**
     * 上树
     *
     * @param userID   挂树的成员QQ号
     * @param groupID  挂树的成员所在公会的QQ群号
     * @param whichOne 挂在第几个boss身上
     * @param rounds   这个boss是第几周目的
     */
    @Insert("insert into tree_list(member_id, group_id, which_one, rounds)" +
            "values(#{userID}, #{groupID}, #{whichOne}, #{rounds})")
    void addHangingTree(long userID, long groupID, int whichOne, int rounds);

    /**
     * 下树
     *
     * @param status   下树状态
     * @param userID   下树的成员QQ号
     * @param whichOne 是哪一个boss
     * @param rounds   第几周目的
     */
    @Update("update tree_list set status=#{status} where member_id=#{userID} and group_id=#{groupID} and which_one=#{whichOne} and rounds=#{rounds}")
    void updateHangingToDown(int status, long userID, long groupID, int whichOne, int rounds);

    /**
     * 查询指定成员的所有挂树记录
     *
     * @param userID 成员QQ号
     * @return 该成员的所有挂树记录
     */
    @Select("select * from tree_list where member_id=#{userID} and group_id=#{groupID}")
    @ResultMap("treeListMap")
    List<TreeList> findAllTreeListByUserID(Long userID, long groupID);
}
